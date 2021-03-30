package com.example.tabbottomkit.demo2

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.tabbottomkit.R
import com.tab.bottom.kit.TabBottomInfo
import com.tab.bottom.kit.TabBottomKitLayout
import com.tab.bottom.kit.util.DisplayUtil

import java.util.ArrayList

class Demo2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo2)

        initTabBottom2()
    }

    private fun initTabBottom2() {
        val hiTabBottomLayout: TabBottomKitLayout = findViewById(R.id.itablayout)
        hiTabBottomLayout.setTabAlpha(0.85f)

        val defaultColor: Int = getResources().getColor(R.color.tabBottomDefaultColor)
        val tintColor: Int = getResources().getColor(R.color.tabBottomTintColor)

        val bottomInfoList: MutableList<TabBottomInfo<*>> = ArrayList()

        val bitmap = BitmapFactory.decodeResource(resources,
            R.drawable.fire, null)
        val bitmap2 = BitmapFactory.decodeResource(resources,
            R.drawable.fire2, null)

        val homeInfo = TabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            defaultColor,
            tintColor
        )
        val infoRecommend = TabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_favorite),
            null,
            defaultColor,
            tintColor
        )

        val infoCategory = TabBottomInfo(
            "分类",
            bitmap,
            bitmap2,
            defaultColor,
            tintColor
        )

        val infoChat = TabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            getString(R.string.if_recommend),
            null,
            defaultColor,
            tintColor
        )

        val infoProfile = TabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            defaultColor,
            tintColor
        )
        bottomInfoList.add(homeInfo)
        bottomInfoList.add(infoRecommend)
        bottomInfoList.add(infoCategory)
        bottomInfoList.add(infoChat)
        bottomInfoList.add(infoProfile)
        hiTabBottomLayout.inflateInfo(bottomInfoList)

        Handler().postDelayed(Runnable {
            bottomInfoList.removeAt(1)
            hiTabBottomLayout.inflateInfo(bottomInfoList)
            hiTabBottomLayout.defaultSelected(homeInfo)
            // 改变某个tab的高度
            val tabBottom = hiTabBottomLayout.findTab(bottomInfoList[1])
            tabBottom?.apply { resetHeight(DisplayUtil.dp2px(100f, resources)) }
        },2000)

        hiTabBottomLayout.addTabSelectedChangeListener { _, _, currentSelectInfo ->
            Toast.makeText(this@Demo2Activity, currentSelectInfo.mName, Toast.LENGTH_SHORT).show()
        }

        hiTabBottomLayout.defaultSelected(homeInfo)

        // 改变某个tab的高度
        val tabBottom = hiTabBottomLayout.findTab(bottomInfoList[2])
        tabBottom?.apply { resetHeight(DisplayUtil.dp2px(100f, resources)) }
    }
}