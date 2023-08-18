package com.example.a5w1h.fragment

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a5w1h.adapter.HistoryAdapter
import com.example.a5w1h.data.DataHelper
import com.example.a5w1h.databinding.FragmentHistoryBinding
import com.example.a5w1h.fragment.LoadFromFile.convertJsonToWordList
import com.example.a5w1h.fragment.LoadFromFile.readFileDirectlyAsText
import com.example.a5w1h.model.Word


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private var data: ArrayList<Word> = ArrayList()
    private var idList: ArrayList<String> = ArrayList()
    private lateinit var dataHelper: DataHelper
    private lateinit var db: SQLiteDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val recyclerView: RecyclerView = binding.rvHistory
        val refreshBtn: ImageButton = binding.refresh
        val deleteAll: ImageButton = binding.deleteAll
        dataHelper = DataHelper(requireContext())
        db = dataHelper.readableDatabase
        data = convertJsonToWordList(readFileDirectlyAsText(resources))
        idList = dataHelper.getAllData(db)
        print(idList.size)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = HistoryAdapter(idList, data, requireContext())
        refreshBtn.setOnClickListener(View.OnClickListener {
            idList = dataHelper.getAllData(db)
            recyclerView.adapter = HistoryAdapter(idList, data, requireContext())
        })
        deleteAll.setOnClickListener(View.OnClickListener {
            dataHelper.deleteAllData(db)
            idList = dataHelper.getAllData(db)
            recyclerView.adapter = HistoryAdapter(idList, data, requireContext())
        })
        return binding.root
    }
}