package com.tab.bottom.kit.tab;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tab.bottom.kit.TabBottomInfo;

import java.util.List;

/**
 * Author: 信仰年轻
 * Date: 2020-09-11 15:17
 * Email: hydznsqk@163.com
 * Des: 用于创建Fragment和切换Fragment
 */
public class TabViewAdapter {

    private FragmentManager mFragmentManager;
    private List<TabBottomInfo<?>> mInfoList;
    private Fragment mCurrentFragment;

    public TabViewAdapter(FragmentManager fragmentManager, List<TabBottomInfo<?>> infoList) {
        this.mFragmentManager = fragmentManager;
        this.mInfoList = infoList;
    }

    /**
     * 实例化Fragment,其实是真正的控制Fragment的显示隐藏和创建
     *
     * @param container
     * @param position
     */
    public void instantiateItem(View container, int position) {
        //开启事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        //先隐藏当前的fragment
        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }

        //根据tag找到 Fragment,如果找不到就创建Fragment,然后添加进来
        String tagName = container.getId() + ":" + position;
        Fragment fragment = mFragmentManager.findFragmentByTag(tagName);
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            fragment = getItem(position);
            if (!fragment.isAdded()) {
                //把Fragment添加到传进来的container中
                transaction.add(container.getId(), fragment, tagName);
            }
        }
        //记录当前的fragment
        mCurrentFragment = fragment;
        //提交事务
        transaction.commitNowAllowingStateLoss();
    }

    /**
     * 根据索引得到Fragment的对象实例
     *
     * @param position
     * @return
     */
    public Fragment getItem(int position) {
        try {
            return mInfoList.get(position).mFragment.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
