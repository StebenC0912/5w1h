package com.example.a5w1h.fragment

import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a5w1h.SelectedWordListInterface
import com.example.a5w1h.adapter.WordAdapter
import com.example.a5w1h.data.DataHelper
import com.example.a5w1h.databinding.FragmentHomeBinding
import com.example.a5w1h.fragment.LoadFromFile.convertJsonToWordList
import com.example.a5w1h.fragment.LoadFromFile.readFileDirectlyAsText
import com.example.a5w1h.model.Word


class HomeFragment : Fragment(), SelectedWordListInterface {
    private var wordList = ArrayList<Word>()
    private var loadList = ArrayList<Word>()
    private lateinit var dataHelper : DataHelper
    private lateinit var db : SQLiteDatabase
    private lateinit var binding: FragmentHomeBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        loadList = convertJsonToWordList(readFileDirectlyAsText(resources))
        val autoAddBtn: Button = binding.autoAdd
        val deleteAll: TextView = binding.deleteAll
        val manualAddBtn: Button = binding.manualAdd
        val startLearn : Button = binding.startLearn
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
        startLearn.setOnClickListener(
            View.OnClickListener {
                if (wordList.size > 0) {
                    dataHelper = DataHelper(requireContext())
                    db = dataHelper.readableDatabase
                    var stringBuilder = StringBuilder()
                    for (i in 0 until wordList.size) {
                        stringBuilder.append(wordList[i].id)
                        stringBuilder.append(",")
                    }
                    dataHelper.insertData(stringBuilder.toString(), db)
                    dataHelper.close()
                    Toast.makeText(requireContext(), "Start learning", Toast.LENGTH_SHORT).show()
                }
            }
        )
        return binding.root
    }
    override fun getSelectedWordList(sortedWordList: ArrayList<Word>) {
        wordList = sortedWordList
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = WordAdapter(wordList, requireContext())
    }
}
