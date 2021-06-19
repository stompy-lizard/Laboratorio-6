package com.example.sonidos

import androidx.appcompat.app.AppCompatActivity
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    // Lista de sonidos.
    private val sounds = mutableListOf<MediaPlayer>()

    // Lista de botones.
    private val buttons = mutableListOf<Button>()

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

        // Se agrega la función a cada botón cuando se presiona.
        for (i in 0 until buttons.size) {
            buttons[i].setOnClickListener {
                onClickListener(i)
            }
        }
    }

    // La función al presionar el boton depende del indice del botón y hace sonar el sonido
    // correspondiente.
    private fun onClickListener(i: Int) {
        println("Playing $i")
        sounds[i].start()
    }
}
