package com.example.a5w1h.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import com.example.a5w1h.model.WordDao
import com.example.a5w1h.model.WordData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class WordRepository (private val wordDao: WordDao){

    val allWords: Flow<List<WordData>> = wordDao.getAllData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun insert(word: String){
        val date = LocalDate.now().toString()
        wordDao.insertData(word, date)
    }
}