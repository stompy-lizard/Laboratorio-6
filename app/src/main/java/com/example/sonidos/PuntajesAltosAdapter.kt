package com.example.sonidos

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PuntajesAltosAdapter(private val context: Activity, private val nombres: Array<String>, private val puntajes: Array<String>)
    : ArrayAdapter<String>(context, R.layout.puntaje_alto, nombres) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.puntaje_alto, null, true)

        val nombreText = rowView.findViewById(R.id.editTextNombre) as TextView
        val puntajeText = rowView.findViewById(R.id.textViewPuntaje) as TextView

        nombreText.text = "Nombre: ${nombres[position]}"
        puntajeText.text = "Puntaje: ${puntajes[position]}"
        return rowView
    }
}
