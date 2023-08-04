package com.example.a5w1h.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a5w1h.R
import com.example.a5w1h.adapter.LetterAdapter
import com.example.a5w1h.adapter.WordAdapter
import com.example.a5w1h.adapter.WordListSortedAdapter
import com.example.a5w1h.databinding.FragmentBottomBinding
import com.example.a5w1h.model.Word
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.json.JSONArray
import java.io.InputStream


class BottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomBinding
    private var wordList = ArrayList<Word>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (resources.displayMetrics.heightPixels * 2 / 3))

        binding = FragmentBottomBinding.inflate(inflater)
        binding.search.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.search.adapter = LetterAdapter(requireContext())

        binding.allWordList.isNestedScrollingEnabled = false
        binding.allWordList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        wordList = convertJsonToWordList(readFileDirectlyAsText())
        binding.allWordList.adapter = WordListSortedAdapter(wordList,requireContext())

        binding.root.setOnTouchListener { _, _ -> true }
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