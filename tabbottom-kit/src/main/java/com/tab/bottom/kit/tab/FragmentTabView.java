package com.tab.bottom.kit.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Author: 信仰年轻
 * Date: 2020-09-11 15:01
 * Email: hydznsqk@163.com
 * Des: 存放Fragment的容器,和底部Tab配合使用
 */
public class FragmentTabView extends FrameLayout {

    private TabViewAdapter mAdapter;
    private int mCurrentPosition;

    public FragmentTabView(@NonNull Context context) {
        this(context,null);
    }

    public FragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置adapter
     * @param adapter
     */
    public void setAdapter(TabViewAdapter adapter){
        if(mAdapter!=null || adapter==null){
            return;
        }
        this.mAdapter=adapter;
        mCurrentPosition=-1;
    }

    /**
     * 设置当前的Fragment,其实就是切换fragment
     * @param position
     */
    public void setCurrentItem(int position){
        if (position < 0 || position >= mAdapter.getCount()) {
            return;
        }
        if(mCurrentPosition!=position){
            mCurrentPosition=position;
            mAdapter.instantiateItem(this,position);
        }
    }

    public TabViewAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 获取当前的索引
     * @return
     */
    public int getCurrentItem() {
        return mCurrentPosition;
    }

    /**
     * 获取当前的fragment
     * @return
     */
    public Fragment getCurrentFragment() {
        if (this.mAdapter == null) {
            throw new IllegalArgumentException("please call setAdapter first.");
        }
        return mAdapter.getCurrentFragment();
    }

}
