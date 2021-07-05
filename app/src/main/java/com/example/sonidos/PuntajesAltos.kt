package com.example.sonidos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class PuntajesAltosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puntajes_altos)
        viewRecord(findViewById(R.id.puntajesAltosList))
    }

    fun viewRecord(puntajesAltosList: ListView){
        val db = DBHandler(this)
        val puntajesAltos: List<Puntaje> = db.viewHighScores()
        val puntajesAltosArrayNombre = Array(puntajesAltos.size){"null"}
        val puntajesAltosArrayPuntaje = Array(puntajesAltos.size){"null"}
        puntajesAltos.forEachIndexed { index, puntaje ->
            puntajesAltosArrayNombre[index] = puntaje.name
            puntajesAltosArrayPuntaje[index] = puntaje.score.toString()
        }
        val puntajesAltosAdapter = PuntajesAltosAdapter(this,puntajesAltosArrayNombre, puntajesAltosArrayPuntaje)
        puntajesAltosList.adapter = puntajesAltosAdapter
    }
}