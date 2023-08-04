package com.example.a5w1h.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a5w1h.R
import com.example.a5w1h.SelectedWordList
import com.example.a5w1h.adapter.WordAdapter
import com.example.a5w1h.databinding.FragmentHomeBinding
import com.example.a5w1h.model.Word
import org.json.JSONArray
import java.io.InputStream


class HomeFragment : Fragment(), SelectedWordList {
    private var wordList = ArrayList<Word>()
    private var loadList = ArrayList<Word>()
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val text: String = readFileDirectlyAsText()
        loadList = convertJsonToWordList(text)
        val autoAddBtn: Button = binding.autoAdd
        val deleteAll: TextView = binding.deleteAll
        val manualAddBtn: Button = binding.manualAdd
        deleteAll.setOnClickListener {
            wordList.clear()
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = WordAdapter(wordList, requireContext())
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
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.adapter = WordAdapter(wordList, requireContext())
            }
        )
        manualAddBtn.setOnClickListener(
            View.OnClickListener {
                BottomFragment(this).show(requireActivity().supportFragmentManager, "TAG")
            })
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

    override fun getSelectedWordList(sortedWordList: ArrayList<Word>) {
        wordList = sortedWordList
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = WordAdapter(wordList, requireContext())
    }

}
