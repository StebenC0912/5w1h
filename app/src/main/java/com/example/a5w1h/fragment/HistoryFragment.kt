package com.example.a5w1h.fragment

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.a5w1h.R
import com.example.a5w1h.adapter.HistoryAdapter
import com.example.a5w1h.data.DataHelper
import com.example.a5w1h.databinding.FragmentHistoryBinding
import com.example.a5w1h.model.Word
import org.json.JSONArray
import java.io.InputStream


class HistoryFragment : Fragment() {
    private lateinit var binding : FragmentHistoryBinding
    private var data : ArrayList<Word> = ArrayList()
    private var idList : ArrayList<String> = ArrayList()
    private lateinit var dataHelper : DataHelper
    private lateinit var db : SQLiteDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val recyclerView : RecyclerView = binding.rvHistory
        dataHelper = DataHelper(requireContext())
        db = dataHelper.readableDatabase
        data = convertJsonToWordList(readFileDirectlyAsText())
        idList = dataHelper.getAllData(db)
        recyclerView.adapter = HistoryAdapter(idList, data, requireContext())
        Toast.makeText(context, "OKOKOK", Toast.LENGTH_SHORT).show()
        return binding.root
    }

    fun convertJsonToWordList(jsonString: String): ArrayList<Word> {
        val wordList = ArrayList<Word>()
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getInt("id")
            val english = jsonObject.getString("english")
            val vietnamese = jsonObject.getString("vietnamese")
            val word = Word(id, english, vietnamese)
            wordList.add(word)
        }
        return wordList
    }

    fun readFileDirectlyAsText(): String {
        val inputStream: InputStream = resources.openRawResource(R.raw.vocabulary_en_vi)
        val inputString = inputStream.bufferedReader().use { it.readText() }
        inputStream.close()
        return inputString
    }




}