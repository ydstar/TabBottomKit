package com.example.tabbottomkit.demo1

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tabbottomkit.R
import com.example.tabbottomkit.fragment.*
import com.tab.bottom.kit.*

import com.tab.bottom.kit.tab.FragmentTabView
import com.tab.bottom.kit.tab.TabViewAdapter
import com.tab.bottom.kit.util.DisplayUtil

import java.util.*

class Demo1Activity : AppCompatActivity() {

    private var mFragmentTabView: FragmentTabView? = null
    private var mTabBottomLayout: TabBottomKitLayout? = null
    private var mCurrentItemIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo1)
        initView()
        initBottomTab()
    }

    /**
     * 初始化view
     */
    private fun initView() {
        mTabBottomLayout = findViewById(R.id.tab_bottom_layout)
        mFragmentTabView = findViewById(R.id.fragment_tab_view)
    }

    /**
     * 初始化底部的Tab和中间的fragment
     */
    private fun initBottomTab() {
        mTabBottomLayout!!.setTabAlpha(0.55f)
        //获取tab数据
        val infoList = getFragmentInfoList()
        //初始化所有的底部tab
        mTabBottomLayout!!.inflateInfo(infoList)
        //初始化所有的Fragment
        initFragmentTabView(infoList)

        //添加tab切换的监听
        mTabBottomLayout!!.addTabSelectedChangeListener { index, prevInfo, nextInfo -> //切换fragment
            mFragmentTabView!!.currentItem = index
            mCurrentItemIndex = index
        }

        //设置默认选中的tab,只要这个方法被调用,上面的监听tab的onTabSelectedChange()方法就会调用,就会设置当前的fragment
        mTabBottomLayout!!.defaultSelected(infoList[mCurrentItemIndex])


        // 改变某个tab的高度
        val tabBottom = mTabBottomLayout?.findTab(infoList[2])
        tabBottom?.apply { resetHeight(DisplayUtil.dp2px(100f, resources)) }
    }

    /**
     * 初始化Fragment
     *
     * @param infoList
     */
    private fun initFragmentTabView(infoList: List<TabBottomInfo<*>>) {
        val adapter = TabViewAdapter(getSupportFragmentManager(), infoList)
        mFragmentTabView!!.adapter = adapter
    }

    /**
     * 获取所有的Fragment所对应的底部Tab数据
     */
    private fun getFragmentInfoList(): List<TabBottomInfo<*>> {
        val infoList: MutableList<TabBottomInfo<*>> =
            ArrayList()
        val defaultColor: Int = getResources().getColor(R.color.tabBottomDefaultColor)
        val tintColor: Int = getResources().getColor(R.color.tabBottomTintColor)
        val homeInfo = TabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            defaultColor,
            tintColor
        )
        homeInfo.mFragment = HomeFragment::class.java
        val infoFavorite = TabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_favorite),
            null,
            defaultColor,
            tintColor
        )
        infoFavorite.mFragment = FavoriteFragment::class.java
//        val infoCategory = TabBottomInfo(
//            "分类",
//            "fonts/iconfont.ttf",
//            getString(R.string.if_category),
//            null,
//            defaultColor,
//            tintColor
//        )
        val bitmap = BitmapFactory.decodeResource(resources,
            R.drawable.fire, null)
        val bitmap2 = BitmapFactory.decodeResource(resources,
            R.drawable.fire2, null)
        val infoCategory = TabBottomInfo(
            "",
            bitmap,
            bitmap2,
            defaultColor,
            tintColor
        )

        infoCategory.mFragment = CategoryFragment::class.java
        val infoRecommend = TabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            getString(R.string.if_recommend),
            null,
            defaultColor,
            tintColor
        )
        infoRecommend.mFragment = RecommendFragment::class.java
        val infoProfile = TabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            defaultColor,
            tintColor
        )
        infoProfile.mFragment = ProfileFragment::class.java
        infoList.add(homeInfo)
        infoList.add(infoFavorite)
        infoList.add(infoCategory)
        infoList.add(infoRecommend)
        infoList.add(infoProfile)

        return infoList
    }
}