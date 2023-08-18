package com.example.a5w1h.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a5w1h.R
import com.example.a5w1h.model.Word

class WordAdapter(private val wordList: ArrayList<Word>, private val context: Context) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {
    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val letterView: TextView = itemView.findViewById(R.id.letter)
        val wordView: TextView = itemView.findViewById(R.id.word)
        val meaningView: TextView = itemView.findViewById(R.id.meaning)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentItem = wordList[position]
        holder.letterView.text = currentItem.origin[0].toString()
        holder.wordView.text = currentItem.origin
        holder.meaningView.text = currentItem.meaning
        holder.deleteBtn.setOnClickListener {
            wordList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, wordList.size)
        }
    }

    override fun getItemCount(): Int {
        return wordList.size
    }
}
