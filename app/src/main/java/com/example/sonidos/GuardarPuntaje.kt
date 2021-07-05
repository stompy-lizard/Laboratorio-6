package com.example.sonidos

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import kotlin.system.exitProcess


class GuardarPuntajeActivity : AppCompatActivity() {
    private var puntaje: Int = 0
    private lateinit var nombre: String
    private lateinit var date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardar_puntaje)

        println("DEBUG_SONIDOS INICIANDO ACTIVIDAD guardando puntaje")
        val intent = getIntent()
        puntaje = intent.getIntExtra("puntaje", 0)

        println("DEBUG_SONIDOS valor de puntaje $puntaje")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDateTime.now().toString()
        } else {
            date = ""
        }

        val guardarPuntajeBtn = findViewById<Button>(R.id.guardarPuntajeBtn)
        guardarPuntajeBtn.setOnClickListener { guardarPuntaje() }
    }

    fun guardarPuntaje() {
        println("DEBUG_SONIDOS guardando puntaje")
        nombre = findViewById<EditText>(R.id.nuevoNombre).text.toString()
        if (nombre.trim() == "") nombre = "Sin nombre"
        val db = DBHandler(this)
        val status = db.addScore(Puntaje(nombre, puntaje, date))
        println("DEBUG_SONIDOS status guardar spuntaje: $status")
        val intent = Intent(this, MainActivity::class.java)
        // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}