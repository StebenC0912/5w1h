package com.example.a5w1h.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@androidx.room.Entity(tableName = "word_table")
data class WordData(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "listIDWord") val listIDWord: String,
    @ColumnInfo(name = "date") val date: String
)
