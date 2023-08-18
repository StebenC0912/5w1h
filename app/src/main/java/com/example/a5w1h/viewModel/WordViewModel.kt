package com.example.a5w1h.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.a5w1h.model.WordData
import com.example.a5w1h.repository.WordRepository

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    val allWords: LiveData<List<WordData>> = repository.allWords.asLiveData()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun insert(word: String) {
            repository.insert(word)
        }
}