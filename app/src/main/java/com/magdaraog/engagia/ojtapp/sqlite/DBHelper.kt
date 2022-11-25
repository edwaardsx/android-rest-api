package com.magdaraog.engagia.ojtapp.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LOG_COL + " LONGTEXT," +
                DATE_COL + " VARCHAR," +
                TIME_COL + " VARCHAR" +")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addLog(log : String, date : String, time: String ) {
        val values = ContentValues()
        values.put(LOG_COL, log)
        values.put(DATE_COL, date)
        values.put(TIME_COL, time)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getLog(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        private const val DATABASE_NAME = "logs"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "logs_table"
        const val ID_COL = "id"
        const val LOG_COL = "log"
        const val DATE_COL = "date"
        const val TIME_COL = "time"
    }
}