package com.example.a5w1h.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a5w1h.R
import com.example.a5w1h.SelectedLetter

class LetterAdapter(private val context : Context, private val listener: SelectedLetter) : RecyclerView.Adapter<LetterAdapter.LetterViewHolder>() {

    private val letterList = listOf<Char>('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                                            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                                            'U', 'V', 'W', 'X', 'Y', 'Z')
    class LetterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val letterView : TextView = itemView.findViewById(R.id.letter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.letter_sort, parent, false)
        return LetterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return letterList.size
    }

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        val currentItem = letterList[position]
        holder.letterView.text = currentItem.toString()
        holder.letterView.setOnClickListener {
            listener?.getSortedLetter(currentItem.toString())
        }
    }


}