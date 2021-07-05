package com.example.sonidos

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import kotlin.random.Random


class JuegoActivity : AppCompatActivity() {

    private var puntaje = 0

    // Lista de sonidos.
    private val sounds = mutableListOf<MediaPlayer>()

    // Lista de botones.
    private val buttons = mutableListOf<Button>()

    // Lista de colores de botones.
    private val buttons_colors = mutableListOf<Int>()

    // Boton de inicio.
    private lateinit var btnstart: Button

    // Mensaje de secuencia correcta o incorrecta.
    private lateinit var message: TextView

    // Secuencia aleatoria a generar.
    private lateinit var sequence: List<Int>

    // Secuencia de botones ingresados por el usuario.
    private val user_sequence = mutableListOf<Int>()

    // Cuando se crea la actividad se definen los sonidos, los botones y los colores de estos.
    // Además se le asigna una función a ejecutar cuando se presionan los botones.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        // Se agregan los sonidos desde los recursos disponibles.
        sounds.add(MediaPlayer.create(this, R.raw.dog))
        sounds.add(MediaPlayer.create(this, R.raw.cat))
        sounds.add(MediaPlayer.create(this, R.raw.horse))
        sounds.add(MediaPlayer.create(this, R.raw.bird))
        sounds.add(MediaPlayer.create(this, R.raw.mosquito))
        sounds.add(MediaPlayer.create(this, R.raw.lion))

        // Se agregan los botones disponibles en el layout a partir de su id.
        buttons.add(findViewById(R.id.button1))
        buttons.add(findViewById(R.id.button2))
        buttons.add(findViewById(R.id.button3))
        buttons.add(findViewById(R.id.button4))
        buttons.add(findViewById(R.id.button5))
        buttons.add(findViewById(R.id.button6))

        // Se agregan los colores de los botones.
        buttons_colors.add(R.color.btn1)
        buttons_colors.add(R.color.btn2)
        buttons_colors.add(R.color.btn3)
        buttons_colors.add(R.color.btn4)
        buttons_colors.add(R.color.btn5)
        buttons_colors.add(R.color.btn6)


        // Poner colores a los botones.
        buttons.forEachIndexed { i, btn ->
            btn.setBackgroundColor(buttons_colors[i])
        }

        // Se agrega la función a cada botón cuando se presiona.
        for (i in 0 until buttons.size) {
            buttons[i].setOnClickListener {
                onClickListener(i)
            }
        }

        // Se define la acción del botón de inicio.
        btnstart = findViewById(R.id.buttonstart)
        btnstart.setOnClickListener {
            onBtnStartPressHandler()
        }

        updatePuntaje()

        for (btn in buttons) btn.isEnabled = false

        // Se inicializa el mensaje vacío.
        message = findViewById(R.id.message)
        message.text = ""
    }

    private fun updatePuntaje() {
        findViewById<TextView>(R.id.puntajeText).text = "Puntaje: $puntaje"

    }

    private fun onBtnStartPressHandler() {
        // demonstración.
        playSequence()
    }

    // Ejecuta la demonstración.
    private fun playSequence() {

        // tiempo entre botones demonstrados(ms).
        val timeStep: Long = 700L

        // tiempo que el botón se muestra en blanco para la secuencia(ms).
        val timePressed: Long = 350L

        // la secuencia se calcula cada vez que se demuestra.
        sequence = List(puntaje + 1) { Random.nextInt(0, 6) }

        // se limpia la secuencia del usuario
        user_sequence.clear()

        // limpiar mensaje
        message.text = ""

        // deshabilitar los botones para la demonstración.
        btnstart.isEnabled = false
        for (btn in buttons) btn.isEnabled = false

        // Demonstración
        sequence.forEachIndexed { i, btnIdx ->
            Handler().postDelayed(Runnable {
                // ejecuta el sonido asociado al botón.
                sounds[btnIdx].start()
                // pone color del botón en blanco para demostrar la secuencia.
                buttons[btnIdx].setBackgroundColor(getResources().getColor(R.color.white))
                Handler().postDelayed(Runnable {
                    // revierte el cambio de color del demo.
                    buttons[btnIdx].setBackgroundColor(buttons_colors[btnIdx])
                    // si es el último botón, se habilitan los botones nuevamente
                    if (i == sequence.size - 1) {
                        btnstart.isEnabled = true
                        for (btn in buttons) btn.isEnabled = true
                    }
                }, timePressed)
            }, timeStep * i + 1000)
        }
    }

    // La función al presionar el boton depende del indice del botón y hace sonar el sonido
    // correspondiente.
    private fun onClickListener(i: Int) {
        // ejecuta el sonido asociado al botón.
        sounds[i].start()

        // siempre y cuando se han presionado botones menos o la cantidad de veces de la secuencia.
        // generada.
        if (user_sequence.size <= sequence.size) {
            // se agrega el botón presionadoa a la secuencia del usuario.
            user_sequence.add(i)
            // se revisa la secuencia del usuario contra la generada.
            // esto se puede optimizar en caso que las secuencias sean muy grandes, siendo de 4
            // valores, esto no es algo que precise.
            user_sequence.forEachIndexed { idx, item ->
                // si las secuencias son distintas, muestra el mensaje de error y deshabilita
                // los botones del juego, solo se puede presionar el botón de inicio para reiniciar
                // el juego.
                if (item != sequence[idx]) {
                    message.text = resources.getText(R.string.msg_fail)
                    message.setTextColor(resources.getColor(R.color.fail))
                    for (btn in buttons) btn.isEnabled = false
                    val db: DBHandler = DBHandler(this)
                    nuevoPuntaje()
                }
                // Si la secuencia es la misma y el tamaño de ambas secuencias es la misma, esto
                // indica que el usuario acertó la secuencia, se muestra el mensaje de éxito y se
                // deshabilitan los botones del juego, solo se permite el botón de inicio para
                // reiniciar el juego.
                else if (idx == sequence.size - 1) {
                    message.text = resources.getText(R.string.msg_success)
                    message.setTextColor(resources.getColor(R.color.success))
                    puntaje = puntaje + 1
                    for (btn in buttons) btn.isEnabled = false
                    updatePuntaje()
                }
            }
        }
    }

    fun nuevoPuntaje() {
        val intent = Intent(this, GuardarPuntajeActivity::class.java)
        intent.putExtra("puntaje", puntaje)
        startActivity(intent)
    }
}
