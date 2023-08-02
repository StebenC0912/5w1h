package com.example.a5w1h.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.a5w1h.fragment.HistoryFragment
import com.example.a5w1h.fragment.HomeFragment
import com.example.a5w1h.fragment.MainFragment
import com.example.a5w1h.fragment.NotificationFragment
import com.example.a5w1h.fragment.SettingFragment

class MyViewPageAdapter(fragmentFragment: MainFragment) :
    FragmentStateAdapter(fragmentFragment) {
    override fun getItemCount(): Int {
        return 4;
    }
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> HistoryFragment()
            2 -> NotificationFragment()
            3 -> SettingFragment()
            else -> HomeFragment()
        }
    }
}