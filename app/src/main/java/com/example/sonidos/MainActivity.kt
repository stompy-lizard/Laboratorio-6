package com.example.sonidos

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    // Lista de sonidos.
    private val sounds = mutableListOf<MediaPlayer>()

    // Lista de botones.
    private val buttons = mutableListOf<Button>()

    // Lista de colores de botones.
    private val buttons_colors = mutableListOf<Int>()

    private lateinit var btnstart: Button
    private lateinit var message: TextView

    private lateinit var sequence: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        buttons_colors.add(R.color.btn1)
        buttons_colors.add(R.color.btn2)
        buttons_colors.add(R.color.btn3)
        buttons_colors.add(R.color.btn4)
        buttons_colors.add(R.color.btn5)
        buttons_colors.add(R.color.btn6)

        // Se agrega la función a cada botón cuando se presiona.
        for (i in 0 until buttons.size) {
            buttons[i].setOnClickListener {
                onClickListener(i)
            }
        }

        btnstart = findViewById(R.id.buttonstart)
        btnstart.setOnClickListener {
            onBtnStartPressHandler()
        }
        message = findViewById(R.id.message)
        message.text = ""

        sequence = List(4) { Random.nextInt(0, 6) }
        println(sequence)
    }

    override fun onStart() {
        super.onStart()

        playSequence()
    }

    private fun onBtnStartPressHandler() {
        playSequence()
    }


    private fun playSequence() {
        var i = 0
        btnstart.isEnabled = false
        for (btn in buttons) {
            btn.isEnabled = false
        }

        sequence.forEachIndexed{ i, btnIdx ->
            val wait: Long = (500 * i).toLong()
            Handler().postDelayed(Runnable {
                println(buttons[btnIdx])
                buttons[btnIdx].performClick()
                buttons[btnIdx].setBackgroundColor(getResources().getColor(R.color.white))
                Handler().postDelayed(Runnable {
                    buttons[btnIdx].setBackgroundColor(buttons_colors[btnIdx])
                    if (i == sequence.size-1) {
                        btnstart.isEnabled = true
                        for (btn in buttons) {
                            btn.isEnabled = true
                        }
                    }
                }, 200)
            }, wait)
        }
    }

    // La función al presionar el boton depende del indice del botón y hace sonar el sonido
    // correspondiente.
    private fun onClickListener(i: Int) {
        println("Playing $i")
        sounds[i].start()
    }
}
