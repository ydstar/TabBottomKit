# TabBottomKit

<img src="https://github.com/ydstar/TabBottomKit/blob/main/preview/show.gif" alt="动图演示效果" width="250px">

轻量级底部tab切换控件

## 导入方式

仅支持`AndroidX`
```
dependencies {
     implementation 'com.android.ydkit:tabbottom-kit:1.0.0'
}
```

## 使用方法

#### 1.在XML布局文件中添加ITabBottomLayout包裹IFragmentTabView
```java
<?xml version="1.0" encoding="utf-8"?>
<com.tab.bottom.kit.ITabBottomLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/tab_bottom_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.tab.bottom.kit.tab.IFragmentTabView
        android:id="@+id/fragment_tab_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</com.tab.bottom.kit.ITabBottomLayout>
```

#### 2.在 Activity中添加代码
初始化底部的Tab和中间的fragment
```java

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
        tabBottom?.apply { resetHeight(IDisplayUtil.dp2px(100f, resources)) }
    }

```

初始化底部的Tab
```java
    private fun getFragmentInfoList(): List<ITabBottomInfo<*>> {
        val infoList: MutableList<ITabBottomInfo<*>> =
            ArrayList()
        val defaultColor: Int = getResources().getColor(R.color.tabBottomDefaultColor)
        val tintColor: Int = getResources().getColor(R.color.tabBottomTintColor)
        val homeInfo = ITabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            defaultColor,
            tintColor
        )
        homeInfo.mFragment = HomeFragment::class.java
        val infoFavorite = ITabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_favorite),
            null,
            defaultColor,
            tintColor
        )
        infoFavorite.mFragment = FavoriteFragment::class.java

        val bitmap = BitmapFactory.decodeResource(resources,
            R.drawable.fire, null)
        val bitmap2 = BitmapFactory.decodeResource(resources,
            R.drawable.fire2, null)
        val infoCategory = ITabBottomInfo(
            "",
            bitmap,
            bitmap2,
            defaultColor,
            tintColor
        )

        infoCategory.mFragment = CategoryFragment::class.java
        val infoRecommend = ITabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            getString(R.string.if_recommend),
            null,
            defaultColor,
            tintColor
        )
        infoRecommend.mFragment = RecommendFragment::class.java
        val infoProfile = ITabBottomInfo(
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
```

初始化fragment
```
    private fun initFragmentTabView(infoList: List<ITabBottomInfo<*>>) {
        val adapter =ITabViewAdapter(getSupportFragmentManager(), infoList)
        mFragmentTabView!!.adapter = adapter
    }
```


## License
```text
Copyright [2021] [ydStar]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
