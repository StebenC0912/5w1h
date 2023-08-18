package com.example.a5w1h.model

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface WordDao {
    @Query("INSERT INTO word_table (listIDWord, date) VALUES (:listIDWord, :date)")
    suspend fun insertData(listIDWord : String, date : String)
    @Query("DELETE FROM word_table WHERE id = :id")
    suspend fun deleteData(id : Int)
    @Query("SELECT * FROM word_table ORDER BY id ASC")
    fun getAllData(): Flow<List<WordData>>
    @Query("DELETE FROM word_table")
    suspend fun deleteAllData()

}