package com.example.a5w1h.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.a5w1h.R
import com.example.a5w1h.adapter.MyViewPageAdapter
import com.example.a5w1h.databinding.FragmentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainFragment : Fragment() {
    private lateinit var myViewPageAdapter: MyViewPageAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        myViewPageAdapter = MyViewPageAdapter(this)
        viewPager = binding.viewPager2
        viewPager.isUserInputEnabled = false
        viewPager.adapter = myViewPageAdapter
        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.home -> viewPager.currentItem = 0
                R.id.history -> reloadHistoryFragment()
                R.id.notifications -> viewPager.currentItem = 2
                R.id.settings -> viewPager.currentItem = 3
            }
            true
        }
        return binding.root
    }
    private fun reloadHistoryFragment() {
        // Initialize the HistoryFragment
        val newHistoryFragment = HistoryFragment() // Replace with your actual HistoryFragment instantiation logic

        // Replace the old HistoryFragment in the adapter
        myViewPageAdapter.replaceFragment(1, newHistoryFragment)

        // Update the current item in the ViewPager2
        viewPager.currentItem = 1
    }
}