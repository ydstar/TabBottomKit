package com.example.tabbottomkit.fragment

import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import com.example.tabbottomkit.R
import com.tab.bottom.kit.ITabBottomLayout

class HomeFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scrollView = view.findViewById<ScrollView>(R.id.scroll_view)
        ITabBottomLayout.clipBottomPadding(scrollView)
    }
}
