package com.aristo.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aristo.view.Fragments.DeveloperInformationFragment
import com.aristo.view.Fragments.NotificationFragment
import com.aristo.view.Fragments.ShopInformationFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ShopInformationFragment()
            1 -> DeveloperInformationFragment()
            else -> ShopInformationFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}