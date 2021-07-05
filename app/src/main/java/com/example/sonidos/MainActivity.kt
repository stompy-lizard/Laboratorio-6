package com.example.sonidos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var highScoresBtn: Button
    private lateinit var juegoBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        println("DEBUG_SONIDOS Iniciando actividad main")

        highScoresBtn = findViewById(R.id.highScoresBtn)
        juegoBtn = findViewById(R.id.juegoBtn)

        highScoresBtn.setOnClickListener {
            startActivity(Intent(this, PuntajesAltosActivity::class.java))
        }

        juegoBtn.setOnClickListener {
            startActivity(Intent(this, JuegoActivity::class.java))
        }
    }
}
