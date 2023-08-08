package com.example.a5w1h.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.a5w1h.model.Word
import java.time.LocalDate


class DataHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        private val DATABASE_NAME = "history.db"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "history"
        val ID_COL = "id"
        val WORD_ID_COL = "word_id"
        val DATE_COL = "date"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID_COL INTEGER PRIMARY KEY AUTOINCREMENT, $WORD_ID_COL TEXT, $DATE_COL TEXT)"
        p0?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertData(listIDWord: String, db: SQLiteDatabase) {
        val values = ContentValues()
        values.put(WORD_ID_COL, listIDWord)
        values.put(DATE_COL, LocalDate.now().toString())
        db.insert(TABLE_NAME, null, values)
    }
    fun getAllData(db : SQLiteDatabase) : ArrayList<String> {
        val list = ArrayList<String>()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while (cursor.moveToNext()) {
            val wordID = cursor.getString(cursor.getColumnIndexOrThrow(WORD_ID_COL))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(DATE_COL))
            val result = "$wordID+$date"
            list.add(result)
        }
        cursor.close()
        return list
    }
    fun deleteAllData(db : SQLiteDatabase) {
        db.execSQL("DELETE FROM $TABLE_NAME")
    }
    fun deleteData(db : SQLiteDatabase, id : String) {
        db.execSQL("DELETE FROM $TABLE_NAME WHERE $ID_COL = $id")
    }
}
