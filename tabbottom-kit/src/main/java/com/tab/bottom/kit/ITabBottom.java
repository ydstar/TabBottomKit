package com.tab.bottom.kit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;


/**
 * Author: 信仰年轻
 * Date: 2020-09-10 14:41
 * Email: hydznsqk@163.com
 * Des: 单个的底部Tab条目
 */
public class ITabBottom extends RelativeLayout implements ITab<ITabBottomInfo<?>> {


    private ImageView mTabImageView;
    private TextView mTabIconView;
    private TextView mTabNameView;

    private ITabBottomInfo<?> mTabInfo;

    public ITabBottom(Context context) {
        this(context, null);
    }

    public ITabBottom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ITabBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.i_tab_bottom, this);
        mTabImageView = findViewById(R.id.iv_image);
        mTabIconView = findViewById(R.id.tv_icon);
        mTabNameView = findViewById(R.id.tv_name);
    }

    /**
     * 设置tab上的信息
     * @param iTabBottomInfo
     */
    @Override
    public void setTabInfo(@NonNull ITabBottomInfo<?> iTabBottomInfo) {
        this.mTabInfo = iTabBottomInfo;
        inflateInfo(false,true);
    }

    /**
     * 填充数据
     * @param selected 是否选中tab
     * @param init 是否是初始化
     */
    private void inflateInfo(boolean selected, boolean init) {
        if (mTabInfo.mTabType == ITabBottomInfo.TabType.ICON) {
            if (init) {
                //隐藏mTabImageView 显示 mTabIconView
                mTabImageView.setVisibility(GONE);
                mTabIconView.setVisibility(VISIBLE);
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), mTabInfo.mIconFont);
                mTabIconView.setTypeface(typeface);
                if (!TextUtils.isEmpty(mTabInfo.mName)) {
                    mTabNameView.setText(mTabInfo.mName);
                }
            }

            if (selected) {
                mTabIconView.setText(TextUtils.isEmpty(mTabInfo.mSelectedIconName) ? mTabInfo.mDefaultIconName : mTabInfo.mSelectedIconName);
                mTabIconView.setTextColor(getTextColor(mTabInfo.mTintColor));
                mTabNameView.setTextColor(getTextColor(mTabInfo.mTintColor));
            } else {
                mTabIconView.setText(mTabInfo.mDefaultIconName);
                mTabIconView.setTextColor(getTextColor(mTabInfo.mDefaultColor));
                mTabNameView.setTextColor(getTextColor(mTabInfo.mDefaultColor));
            }

        } else if (mTabInfo.mTabType == ITabBottomInfo.TabType.BITMAP) {
            if (init) {
                //隐藏mTabIconView 显示 mTabImageView
                mTabImageView.setVisibility(VISIBLE);
                mTabIconView.setVisibility(GONE);
                if (!TextUtils.isEmpty(mTabInfo.mName)) {
                    mTabNameView.setText(mTabInfo.mName);
                }
            }
            if (selected) {
                mTabImageView.setImageBitmap(mTabInfo.mSelectedBitmap);
                mTabNameView.setTextColor(getTextColor(mTabInfo.mTintColor));
            } else {
                mTabImageView.setImageBitmap(mTabInfo.mDefaultBitmap);
                mTabNameView.setTextColor(getTextColor(mTabInfo.mDefaultColor));
            }
        }
    }

    @ColorInt
    private int getTextColor(Object color) {
        if (color instanceof String) {
            return Color.parseColor((String) color);
        } else {
            return (int) color;
        }
    }

    @Override
    public void resetHeight(int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        mTabNameView.setVisibility(View.GONE);
    }


    /**
     * 选择的tab切换改变其ui为选中状态,和改变其他tab为默认状态
     * @param index
     * @param prevInfo
     * @param currentSelectInfo
     */
    public void changeSelectTabAndResetOtherTab(int index, ITabBottomInfo<?> prevInfo, ITabBottomInfo<?> currentSelectInfo) {
        // 默认情况下        tabInfo =首页    prevInfo = null   currentSelectInfo=首页
        // 这个时候点击了推荐  tabInfo =推荐   prevInfo = 首页    currentSelectInfo=推荐
        if(prevInfo !=mTabInfo && currentSelectInfo!=mTabInfo){
            return;
        }
        //重复的点击自己
        if(prevInfo == currentSelectInfo){
            return;
        }
        if(prevInfo==mTabInfo){
            inflateInfo(false,false);
        }else{
            inflateInfo(true,false);
        }

    }

    public ITabBottomInfo<?> getTabInfo() {
        return mTabInfo;
    }
}
