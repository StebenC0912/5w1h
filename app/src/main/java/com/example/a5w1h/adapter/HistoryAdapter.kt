package com.example.a5w1h.adapter

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.a5w1h.R
import com.example.a5w1h.data.DataHelper
import com.example.a5w1h.model.Word

class HistoryAdapter(
    private val wordList: ArrayList<String>,
    private val data: ArrayList<Word>,
    private val context: Context
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private lateinit var dataHelper : DataHelper
    private lateinit var db : SQLiteDatabase
    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordList: TextView = itemView.findViewById(R.id.wordList)
        val date: TextView = itemView.findViewById(R.id.date)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = wordList[position]
        var intBuilder: StringBuilder = StringBuilder()
        var stringBuilder = StringBuilder()
        var date : String = currentItem.substring(currentItem.indexOf('+') + 1, currentItem.length)
        for (i in 0 .. currentItem.length - 1) {
            if (currentItem[i] == ',') {
                val id = intBuilder.toString().toInt()
                intBuilder.clear()
                stringBuilder.append(data[id - 1].origin)
                stringBuilder.append(" ")
            } else if (currentItem[i] == '+') {
                break
            } else {
                intBuilder.append(currentItem[i])
            }
        }
        holder.wordList.text = stringBuilder.toString()
        holder.date.text = date
        holder.deleteBtn.setOnClickListener {
            dataHelper = DataHelper(context)
            db = dataHelper.writableDatabase
            // Delete data from the database using the new position
            dataHelper.deleteData(db, position.toString())
            // Notify the adapter about the item removal
            wordList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, wordList.size)
        }
    }
}