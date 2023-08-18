package com.example.a5w1h.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.a5w1h.fragment.HistoryFragment
import com.example.a5w1h.fragment.HomeFragment
import com.example.a5w1h.fragment.MainFragment
import com.example.a5w1h.fragment.NotificationFragment
import com.example.a5w1h.fragment.SettingFragment

class MyViewPageAdapter(fragmentFragment: MainFragment) : FragmentStateAdapter(fragmentFragment) {
    private val fragmentList = mutableListOf<Fragment>()

    init {
        // Initialize the fragmentList with default fragments
        fragmentList.addAll(
            listOf(
                HomeFragment(),
                HistoryFragment(),
                NotificationFragment(),
                SettingFragment()
            )
        )
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun replaceFragment(position: Int, newFragment: Fragment) {
        fragmentList[position] = newFragment
        notifyDataSetChanged()
    }
}