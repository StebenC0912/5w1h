package com.example.a5w1h.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a5w1h.ConnectBottomInterface
import com.example.a5w1h.R
import com.example.a5w1h.model.Word

class WordListSortedAdapter(
    private val wordList: ArrayList<Word>,
    private val context: Context,
    private val listener: ConnectBottomInterface
) :
    RecyclerView.Adapter<WordListSortedAdapter.WordListSortedViewHolder>() {
    class WordListSortedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordView: TextView = itemView.findViewById(R.id.word)
        val meaningView: TextView = itemView.findViewById(R.id.mean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListSortedViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.word_sort, parent, false)
        return WordListSortedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: WordListSortedViewHolder, position: Int) {
        val currentItem = wordList[position]
        holder.wordView.text = currentItem.origin
        holder.meaningView.text = currentItem.meaning
        holder.itemView.setOnClickListener {
            listener.getSelectedWord(currentItem)
        }
    }

}