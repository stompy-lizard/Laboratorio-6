package com.example.sonidos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import java.time.LocalDateTime

//creating the database logic, extending the SQLiteOpenHelper base class
class DBHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ScoresDB"
        private const val TABLE_NAME = "Scores"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_SCORE = "score"
        private const val KEY_DATE = "date"
        private const val KEY_DATE_DEFAULT = "not supported"
        private const val HIGH_SCORES_LIMIT = 5
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $TABLE_NAME ("
                    + "$KEY_ID INTEGER PRIMARY KEY,"
                    + "$KEY_NAME TEXT,"
                    + "$KEY_SCORE INTEGER,"
                    + "$KEY_DATE TEXT DEFAULT '$KEY_DATE_DEFAULT'"
                    + ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addScore(puntaje: Puntaje): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, puntaje.name)
        contentValues.put(KEY_SCORE, puntaje.score)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            contentValues.put(KEY_DATE, LocalDateTime.now().toString())
        }

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }

    fun viewHighScores(): List<Puntaje> {
        val highPuntajeList: ArrayList<Puntaje> = ArrayList()
        val db = this.readableDatabase
        val query =
            "SELECT * FROM $TABLE_NAME ORDER BY $KEY_SCORE DESC, $KEY_ID ASC LIMIT $HIGH_SCORES_LIMIT"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        var name: String
        var score: Int
        var date: String
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                score = cursor.getInt(cursor.getColumnIndex(KEY_SCORE))
                date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                highPuntajeList.add(Puntaje(name = name, score = score, date = date))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return highPuntajeList
    }
}