package edu.ap.bevers.les2_intents.persistance

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.ArrayList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_QUOTES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_TABLE_QUOTES)
        onCreate(db)
    }

    // loop through all rows and adding to Students list
    @SuppressLint("Range")
    fun allQuotes(): ArrayList<String> {
        val studentsArrayList = ArrayList<String>()
        var author: String
        var quote: String
        val db = this.readableDatabase
        //val cursor = db.rawQuery(SELECT_STUDENTS, null)

        val projection = arrayOf(KEY_ID, BODY, AUTHOR, QUOTESOURCE)
        val selection = null
        val selectionArgs = null
        val sortOrder = "$AUTHOR DESC"

        val cursor = db.query(
            TABLE_QUOTES,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        if (cursor.moveToFirst()) {
            do {
                author = cursor.getString(cursor.getColumnIndex(AUTHOR))
                quote = cursor.getString(cursor.getColumnIndex(BODY))
            } while (cursor.moveToNext())
        }
        cursor.close()

        return studentsArrayList
    }

    fun addQuote(body: String, author: String, quotesource: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(BODY, body)
        values.put(AUTHOR, author)
        values.put(QUOTESOURCE, quotesource)

        return db.insert(TABLE_QUOTES, null, values)
    }

    companion object {

        var DATABASE_NAME = "quote_database"
        private val DATABASE_VERSION = 7
        private val TABLE_QUOTES = "quotes"
        private val KEY_ID = "id"
        private val BODY = "body"
        private val AUTHOR = "author"
        private val QUOTESOURCE = "quotesource"

        private val CREATE_TABLE_QUOTES = ("CREATE TABLE "
                + TABLE_QUOTES + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + BODY + " TEXT," + AUTHOR + " TEXT," + QUOTESOURCE + " TEXT);")

        private val DELETE_TABLE_QUOTES = "DROP TABLE IF EXISTS $TABLE_QUOTES"

        //private val SELECT_STUDENTS = "SELECT * FROM $TABLE_STUDENTS"
    }
}