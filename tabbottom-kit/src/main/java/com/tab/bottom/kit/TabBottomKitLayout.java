package com.tab.bottom.kit;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.tab.bottom.kit.util.DisplayUtil;
import com.tab.bottom.kit.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 信仰年轻
 * Date: 2020-09-10 15:12
 * Email: hydznsqk@163.com
 * Des: 1.透明度和底部透出，列表可渲染高度问题
 *      2.中间高度超过，凸起布局
 *
 * 当前自定义控件,包含中间的内容view,也包含底部的Tab(默认高度50,而且支持切换和凸起布局)
 * 思路:
 *      1.在外界创建Tab,然后通过List传进来,去初始化所有的tab,并进行底部排列
 *      2.可以先把当前容器中的所有view清除了(只是底部的所有view),不包括中间的内容view
 *      3.添加背景,支持半透明
 *      4.根据 屏幕宽度 / tab的数量 = 单个tab的宽度
 *      5.创建 用来装Tab的容器 FrameLayout
 *      7.for循环List,然后创建TabBottom,然后塞到FrameLayout容器中
 *      8.记得设置FrameLayout的params,位于底部,高度为默认的50dp
 *      9.添加底部导航的顶端的线条
 *      10.设置内容区域的底部Padding
 */
public class TabBottomKitLayout extends FrameLayout
        implements ITabLayout<TabBottom, TabBottomInfo<?>> {

    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    //TabBottom高度
    private static final float TAB_BOTTOM_HEIGHT = 50;

    //传进来的所有tab的数据
    private List<TabBottomInfo<?>> mInfoList;

    //当前选中的tab的info
    private TabBottomInfo<?> mSelectInfo;

    private List<TabBottom> mTabBottomList = new ArrayList<>();

    private float mBottomAlpha = 1f;

    //TabBottom的头部线条高度
    private float mBottomLineHeight = 0.5f;

    //TabBottom的头部线条颜色
    private String mBottomLineColor = "#dfe0e1";

    private OnTabSelectedListener<TabBottomInfo<?>> mListener;
    public TabBottomKitLayout(@NonNull Context context) {
        super(context);
    }

    public TabBottomKitLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabBottomKitLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化所有的tab并填充数据
     */
    @Override
    public void inflateInfo(@NonNull List<TabBottomInfo<?>> infoList) {
        if(infoList.isEmpty()){
            return;
        }
        this.mInfoList=infoList;
        //移除之前添加的view,不好包含中间的内容view
        for(int x=getChildCount()-1;x>0;x--){
            removeViewAt(x);
        }
        mSelectInfo=null;
        mTabBottomList.clear();

        //添加背景
        addBackground();


        int height = DisplayUtil.dp2px(TAB_BOTTOM_HEIGHT, getResources());
        //屏幕的宽度 / tab的总数量 = 单个tab的宽度
        int width = DisplayUtil.getDisplayWidthInPx(getContext()) / infoList.size();
        FrameLayout tabContainer = new FrameLayout(getContext());
        tabContainer.setTag(TAG_TAB_BOTTOM);

        for(int x=0;x<infoList.size();x++){
            final TabBottomInfo<?> info = infoList.get(x);
            //Tips：为何不用LinearLayout：当动态改变child大小后Gravity.BOTTOM会失效
            LayoutParams params = new LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin = x * width;

            //每个tabBottom 对应一个 listener
            TabBottom tabBottom = new TabBottom(getContext());
            mTabBottomList.add(tabBottom);
            tabBottom.setTabInfo(info);

            tabContainer.addView(tabBottom, params);
            tabBottom.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //选中指定tab为选中样式,并重置其他的tab为默认样式
                    onSelected(info);

                }
            });
        }
        LayoutParams flPrams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        flPrams.gravity = Gravity.BOTTOM;
        addBottomLine();
        addView(tabContainer, flPrams);
        setContentViewBottomPadding();
    }

    /**
     * 底部导航的背景
     */
    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.i_bottom_layout_bg, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(TAB_BOTTOM_HEIGHT, getResources()));
        params.gravity = Gravity.BOTTOM;
        addView(view, params);
        view.setAlpha(mBottomAlpha);
    }

    /**
     * 底部导航的顶端的线条
     */
    private void addBottomLine() {
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(mBottomLineColor));

        LayoutParams bottomLineParams =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(mBottomLineHeight, getResources()));
        bottomLineParams.gravity = Gravity.BOTTOM;
        bottomLineParams.bottomMargin = DisplayUtil.dp2px(TAB_BOTTOM_HEIGHT - mBottomLineHeight, getResources());
        addView(bottomLine, bottomLineParams);
        bottomLine.setAlpha(mBottomAlpha);
    }

    /**
     * 修复内容区域的底部Padding
     */
    private void setContentViewBottomPadding() {
        if (!(getChildAt(0) instanceof ViewGroup)) {
            return;
        }
        ViewGroup rootView = (ViewGroup) getChildAt(0);
        ViewGroup targetView = ViewUtil.findTypeView(rootView, RecyclerView.class);
        if (targetView == null) {
            targetView = ViewUtil.findTypeView(rootView, ScrollView.class);
        }
        if (targetView == null) {
            targetView = ViewUtil.findTypeView(rootView, AbsListView.class);
        }
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, DisplayUtil.dp2px(TAB_BOTTOM_HEIGHT, getResources()));
            targetView.setClipToPadding(false);
        }
    }


    /**
     * 选中指定的tab,并重置其他的tab的UI样式
     * @param nextInfo
     */
    private void onSelected(@NonNull TabBottomInfo<?> nextInfo) {
        for (TabBottom tabBottom : mTabBottomList) {
            //当前选择的tab索引  上一个选择的tab ,这次选择的tab
            tabBottom.changeSelectTabAndResetOtherTab(mInfoList.indexOf(nextInfo), mSelectInfo, nextInfo);
        }
        if(mListener!=null){
            mListener.onTabSelectedChange(mInfoList.indexOf(nextInfo), mSelectInfo, nextInfo);
        }

        this.mSelectInfo = nextInfo;
    }

    /**
     * 根据数据找到对应的tab
     * @param info
     * @return
     */
    @Override
    public TabBottom findTab(@NonNull TabBottomInfo<?> info) {
        ViewGroup ll = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof TabBottom) {
                TabBottom tab = (TabBottom) child;
                if (tab.getTabInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    /**
     * 默认选中
     * @param defaultInfo
     */
    @Override
    public void defaultSelected(@NonNull TabBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<TabBottomInfo<?>> listener) {
        mListener = listener;
    }

    /**
     * 设置透明度
     * @param alpha
     */
    public void setTabAlpha(float alpha){
        this.mBottomAlpha =alpha;
    }


    /**
     * 设置targetView的底部padding是TAB_BOTTOM_HEIGHT的高度
     * @param targetView
     */
    public static void clipBottomPadding(ViewGroup targetView) {
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, DisplayUtil.dp2px(TAB_BOTTOM_HEIGHT));
            targetView.setClipToPadding(false);
        }
    }

    /**
     * 适配折叠屏,重新计算宽高
     */
    public void resizeITabBottomLayout(){
        int width = DisplayUtil.getDisplayWidthInPx(getContext()) / mInfoList.size();
        ViewGroup frameLayout = (ViewGroup) getChildAt(getChildCount() - 1);
        int childCount = frameLayout.getChildCount();
        for(int i=0;i<childCount;i++){
            View button = frameLayout.getChildAt(i);
            FrameLayout.LayoutParams params = (LayoutParams) button.getLayoutParams();

            params.width=width;
            params.leftMargin=i*width;
            button.setLayoutParams(params);
        }
    }
}
