# TabBottomKit

## YdKit通用组件库
YdKit 是一组功能丰富的 Android 通用组件。

* [LogKit](https://github.com/ydstar/LogKit) — 轻量级的 Android 日志系统。
* [RestfulKit](https://github.com/ydstar/RestfulKit) — 简洁但不简单的 Android 网络组件库。
* [StorageKit](https://github.com/ydstar/StorageKit) — 高性能 Android 离线缓存框架。
* [ExecutorKit](https://github.com/ydstar/ExecutorKit) — 简洁易用的 Android 多线程操作框架。
* [CrashKit](https://github.com/ydstar/CrashKit) — 简洁易用的 Android Crash日志捕捉组件。
* [PermissionKit](https://github.com/ydstar/PermissionKit) — 简洁易用的 Android 权限请求组件。
* [NavigationBarKit](https://github.com/ydstar/NavigationBarKit) — 简洁易用的 Android 顶部导航条组件。
* [RefreshKit](https://github.com/ydstar/RefreshKit) — 简洁易用的 Android 下拉刷新和上拉加载组件。
* [AdapterKit](https://github.com/ydstar/AdapterKit) — 简洁易用的 Android 列表组件。
* [BannerKit](https://github.com/ydstar/BannerKit) — 简洁易用的 Android 无限轮播图组件。
* [TabBottomKit](https://github.com/ydstar/TabBottomKit) — 简洁易用的 Android 底部导航组件。

## 效果预览
<img src="https://github.com/ydstar/TabBottomKit/blob/main/preview/show.gif" alt="动图演示效果" width="250px">

轻量级底部tab切换控件

## 导入方式

仅支持`AndroidX`
```
dependencies {
     implementation 'com.android.ydkit:tabbottom-kit:1.0.1'
}
```

## 使用方法
#### 关于矢量图标的制作和使用,可以参考[矢量字体图标制作并使用](https://www.jianshu.com/p/432d3b23384c)
#### 1.在XML布局文件中添加ITabBottomLayout包裹IFragmentTabView
```java
<?xml version="1.0" encoding="utf-8"?>
<com.tab.bottom.kit.TabBottomKitLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/tab_bottom_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.tab.bottom.kit.tab.FragmentTabView
        android:id="@+id/fragment_tab_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</com.tab.bottom.kit.TabBottomKitLayout>
```

#### 2.在 Activity中添加代码
初始化底部的Tab和内容区域的fragment
```java

    private fun initBottomTabAndFragment() {
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
```

初始化fragment
```
    private fun initFragmentTabView(infoList: List<TabBottomInfo<*>>) {
        val adapter =TabViewAdapter(getSupportFragmentManager(), infoList)
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
