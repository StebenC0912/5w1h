package com.example.a5w1h.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a5w1h.R
import com.example.a5w1h.adapter.WordAdapter
import com.example.a5w1h.model.Word
import org.json.JSONArray
import java.io.InputStream


class HomeFragment : Fragment() {
    private var wordList = ArrayList<Word>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_home, container,
            false
        )
        val text: String = readFileDirectlyAsText("vocabulary_en_vi.txt")
        val loadList: ArrayList<Word> = convertJsonToWordList(text)
        val autoAddBtn: Button = view.findViewById(R.id.autoAdd)
        val deleteAll : TextView = view.findViewById(R.id.deleteAll)
        deleteAll.setOnClickListener {
            wordList.clear()
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = WordAdapter(wordList, requireContext())
        }
        autoAddBtn.setOnClickListener(
            View.OnClickListener {
                if (wordList.size > 0) {
                    wordList.clear()
                }
                for (i in 0..4) {
                    val randomIndex = (0 until loadList.size).random()
                    wordList.add(loadList[randomIndex])
                }
                val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = WordAdapter(wordList, requireContext())
            }
        )
        return view
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
    fun readFileDirectlyAsText(fileName: String): String {
        val inputStream: InputStream = resources.openRawResource(R.raw.vocabulary_en_vi)
        val inputString = inputStream.bufferedReader().use { it.readText() }
        inputStream.close()
        return inputString
    }
}
