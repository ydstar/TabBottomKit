package com.tab.bottom.kit;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Author: 信仰年轻
 * Date: 2020-09-09 16:02
 * Email: hydznsqk@163.com
 * Des:
 */
public interface ITabLayout<Tab extends ViewGroup, Data> {

    /**
     * 初始化所有的tab
     * @param data
     */
    void inflateInfo(@NonNull List<Data> data);

    /**
     * 根据数据找到对应的tab
     *
     * @param data
     * @return
     */
    Tab findTab(@NonNull Data data);

    /**
     * 默认选中
     * @param defaultInfo
     */
    void defaultSelected(@NonNull Data defaultInfo);


    /**
     * 添加选中tab的点击监听
     */
    void addTabSelectedChangeListener(OnTabSelectedListener<Data> listener);


    //对外的回调接口
    interface OnTabSelectedListener<Data> {
        void onTabSelectedChange(int index, Data prevInfo, Data nextInfo);
    }

}
