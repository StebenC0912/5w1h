package com.example.a5w1h.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a5w1h.R
import com.example.a5w1h.ConnectBottomInterface
import com.example.a5w1h.SelectedWordListInterface
import com.example.a5w1h.adapter.LetterAdapter
import com.example.a5w1h.adapter.WordListSortedAdapter
import com.example.a5w1h.databinding.FragmentBottomBinding
import com.example.a5w1h.fragment.LoadFromFile.readFileDirectlyAsText
import com.example.a5w1h.model.Word
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.json.JSONArray
import java.io.InputStream


class BottomFragment(private val listener: SelectedWordListInterface) : BottomSheetDialogFragment(),
    ConnectBottomInterface {

    private lateinit var binding: FragmentBottomBinding
    private var wordList = ArrayList<Word>()
    private var loadList = ArrayList<Word>()
    private var selectedWord = ""
    private var selectedWordList = ArrayList<Word>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomBinding.inflate(inflater)
        binding.search.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.search.adapter = LetterAdapter(requireContext(), this)

        binding.allWordList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.allWordList.isNestedScrollingEnabled = true;
        wordList = LoadFromFile.convertJsonToWordList(readFileDirectlyAsText(resources))
        loadList = wordList.toList() as ArrayList<Word>
        binding.allWordList.adapter = WordListSortedAdapter(loadList, requireContext(), this)

        binding.selectedWord.text = selectedWord
        binding.totalWord.text = "${selectedWordList.size} /5"
        binding.save.setOnClickListener {
            if (selectedWordList.size < 5) {
                Toast.makeText(context, "Not enough word", Toast.LENGTH_SHORT).show()
            } else {
                listener.getSelectedWordList(selectedWordList)
                dismiss()
            }
        }
        binding.controlBar.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dismiss()
                    true
                }
                else -> false
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set peek height to control the initial visible height
        val peekHeight = resources.getDimensionPixelSize(R.dimen.peek_height)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, peekHeight)

        // Disable dragging to prevent expanding
        dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            ?.let { bottomSheet ->
                BottomSheetBehavior.from(bottomSheet).apply {
                    isDraggable = false
                    state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
    }


    override fun getSortedLetter(sortedLetter: String) {
        val sortedList = ArrayList<Word>()
        for (i in 0 until wordList.size) {
            if (wordList[i].origin[0].toString().uppercase() == sortedLetter) {
                sortedList.add(wordList[i])
            }
        }
        loadList = sortedList.toList() as ArrayList<Word>
        binding.allWordList.adapter = WordListSortedAdapter(loadList, requireContext(), this)

    }

    override fun getSelectedWord(sortedWord: Word) {
        if (selectedWordList.contains(sortedWord) || selectedWordList.size >= 5) return;
        selectedWord += sortedWord.origin
        if (selectedWordList.size < 3) selectedWord += ", "
        else if (selectedWordList.size == 3) selectedWord += " and "
        else if (selectedWordList.size == 4) selectedWord =
            selectedWord.substring(0, selectedWord.length - 2)
        binding.selectedWord.text = selectedWord
        selectedWordList.add(sortedWord)
        binding.totalWord.text = "${selectedWordList.size} /5"
    }


}