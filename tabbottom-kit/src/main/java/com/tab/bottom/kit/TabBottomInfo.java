package com.tab.bottom.kit;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * Author: 信仰年轻
 * Date: 2020-09-10 14:47
 * Email: hydznsqk@163.com
 * Des:
 */
public class TabBottomInfo<Color> {

    public enum TabType{
        BITMAP,ICON
    }


    public Class<? extends Fragment> mFragment;
    public String mName;
    public Bitmap mDefaultBitmap;
    public Bitmap mSelectedBitmap;

    /**
     * Tips：在Java代码中直接设置iconfont字符串无效，需要定义在string.xml
     */
    public String mIconFont;
    public String mDefaultIconName;
    public String mSelectedIconName;
    public Color mDefaultColor;
    public Color mTintColor;

    public TabType mTabType;

    public TabBottomInfo(String name,
                         Bitmap defaultBitmap,
                         Bitmap selectedBitmap,
                         Color defaultColor,
                         Color tintColor) {
        this.mName = name;
        this.mDefaultBitmap = defaultBitmap;
        this.mSelectedBitmap = selectedBitmap;
        this.mDefaultColor = defaultColor;
        this.mTintColor = tintColor;
        this.mTabType = TabType.BITMAP;
    }

    public TabBottomInfo(String name,
                         String iconFont,
                         String defaultIconName,
                         String selectedIconName,
                         Color defaultColor,
                         Color tintColor) {
        this.mName = name;
        this.mIconFont = iconFont;
        this.mDefaultIconName = defaultIconName;
        this.mSelectedIconName = selectedIconName;
        this.mDefaultColor = defaultColor;
        this.mTintColor = tintColor;
        this.mTabType = TabType.ICON;
    }
}


