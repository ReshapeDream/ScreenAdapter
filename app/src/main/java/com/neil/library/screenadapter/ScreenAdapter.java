package com.neil.library.screenadapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.neil.customattrhelper.CustomAttr;
import com.neil.customattrhelper.CustomAttrsLayoutInflater;

import java.util.ArrayList;

public class ScreenAdapter {
    private static int aimW=0;
    private static int aimH=0;
    private static float sScaleX=0.0f;
    private static float sScaleY=0.0f;

    public static void setAimScreenWH(int w, int h) {
        aimW = w;
        aimH = h;
    }

    public static View inflater(Context context, int layoutId) {
        return inflater(context, layoutId, false);
    }

    /**
     * @param context
     * @param layoutId
     * @param adapteScreenSwitch 是否适配横竖屏切换
     *                           if true,横屏时 width取较小的，height取较大的，否则，取系统返回的值
     *                           例如 1920*1080的屏幕，竖屏时width=1080,height=1920,横屏时系统返回的width=1920 height=1080;
     *                           如果adapteScreenSwitch 为true,则横屏时计算比例时使用的依然是width=1080,height=1920
     * @return
     */
    public static View inflater(Context context, int layoutId, final boolean adapteScreenSwitch) {
        final CustomAttrsLayoutInflater inflater = new CustomAttrsLayoutInflater(context);
        CustomAttr[] customAttrs = new CustomAttr[]{new CustomAttr(R.attr.aimScreenHeight, 0),
                new CustomAttr(R.attr.aimScreenWidth, 0)};
        inflater.setCustomAttrs(customAttrs);
        final View rootView = inflater.inflate(layoutId, null);
        calculateScale(rootView,inflater,adapteScreenSwitch);
        if(sScaleX>0f&&sScaleY>0f){
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    reSizeView(rootView);
                    //完成后移除监听
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
        return rootView;
    }

    /**
     * 计算缩放比例
     * @param rootView
     * @param inflater
     * @param adapteScreenSwitch
     */
    private static void calculateScale(View rootView,CustomAttrsLayoutInflater inflater,boolean adapteScreenSwitch){
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int width, height;
        width = adapteScreenSwitch ? Math.min(displayMetrics.heightPixels, displayMetrics.widthPixels) : displayMetrics.widthPixels;
        height = adapteScreenSwitch ? Math.max(displayMetrics.heightPixels, displayMetrics.widthPixels) : displayMetrics.heightPixels;
        if(aimW!=0&&aimH!=0){
            sScaleX=(float)aimW/width;
            sScaleY=(float)aimH/height;
        }else{
            ArrayList<CustomAttr> tag = (ArrayList<CustomAttr>) rootView.getTag(inflater.getTagId());
            if (tag.isEmpty()) {
                return;
            }
            for (CustomAttr attr : tag) {
                if (attr.attrId == R.attr.aimScreenHeight) {
                    sScaleY = height / attr.getFloatValue();
                } else if (attr.attrId == R.attr.aimScreenWidth) {
                    sScaleX = width / attr.getFloatValue();
                }
            }
        }
    }

    /**
     * 获取自定义属性，重新计算View的大小
     *
     * @param rootView
     */
    private static void reSizeView(View rootView) {
        if (rootView instanceof ViewGroup) {
            calculateViewGroup((ViewGroup) rootView, sScaleX, sScaleY);
        } else {
            calculateChild(rootView, sScaleX, sScaleY);
        }
    }

    /**
     * ViewGroup 循环子View
     *
     * @param rootView
     * @param scaleX
     * @param scaleY
     */
    private static void calculateViewGroup(ViewGroup rootView, float scaleX, float scaleY) {
        int childCount = rootView.getChildCount();
        //先计算自己
        calculateChild(rootView, scaleX, scaleY);
        //循环计算子View
        for (int i = 0; i < childCount; i++) {
            View childAt = rootView.getChildAt(i);
            calculateChild(childAt, scaleX, scaleY);
            if (childAt instanceof ViewGroup) {
                calculateViewGroup((ViewGroup) childAt, scaleX, scaleY);
            }
        }
    }

    /**
     * 子View
     *
     * @param child
     * @param scaleX
     * @param scaleY
     */
    private static void calculateChild(View child, float scaleX, float scaleY) {
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        layoutParams.width = layoutParams.width < 0 ? child.getMeasuredWidth() : (int) (layoutParams.width * scaleX + 1 - scaleX);
        layoutParams.height = layoutParams.height < 0 ? child.getMeasuredHeight() : (int) (layoutParams.height * scaleY + 1 - scaleY);
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginParams.leftMargin = (int) (marginParams.leftMargin * scaleX + 1 - scaleX);
            marginParams.rightMargin = (int) (marginParams.rightMargin * scaleX + 1 - scaleX);
            marginParams.topMargin = (int) (marginParams.topMargin * scaleY + 1 - scaleY);
            marginParams.bottomMargin = (int) (marginParams.bottomMargin * scaleY + 1 - scaleY);
        }
        child.setLayoutParams(layoutParams);
        child.setPadding((int) (child.getPaddingLeft() * scaleX),
                (int) (child.getPaddingTop() * scaleY),
                (int) (child.getPaddingRight() * scaleX),
                (int) (child.getPaddingBottom() * scaleY));
        //字体大小设置
        if (child instanceof TextView) {
            float srcSize = ((TextView) child).getTextSize();
            ((TextView) child).setTextSize(TypedValue.COMPLEX_UNIT_PX, srcSize * Math.min(scaleX, scaleY));
        }
    }
}
