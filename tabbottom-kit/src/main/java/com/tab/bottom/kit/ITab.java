package com.tab.bottom.kit;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * Author: 信仰年轻
 * Date: 2020-09-09 16:02
 * Email: hydznsqk@163.com
 * Des:
 */
public interface ITab<Data>  {

    /**
     * 设置tab上的信息
     *
     * @param data
     */
    void setTabInfo(@NonNull Data data);

    /**
     * 动态修改某个item的大小
     *
     * @param height
     */
    void resetHeight(@Px int height);
}
