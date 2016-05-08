package com.example.myview.weidge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.myview.R;

/**
 * Created by Administrator on 2016/5/5.
 */
public class MyPanelView extends View{
    private int mWidth;
    private int mHeight;
    private int mPercent;
    //刻度宽度
    private float mTikeWidth;
    //第二个弧的宽度
    private int mScendArcWidth;
    //最小圆的半径
    private int mMinCircleRadius;
    //最小圆的颜色
    private int mMinCircleColor;
    // 文字矩形的宽
    private int mRectWidth;
    //文字矩形的高
    private int mRectHeight;
    //文字内容
    private String mText="";
    //文字的大小
    private int mTextSize;
    //文字的颜色
    private int mTextColor;
    private int mArcColor;

    //刻度的个数
    private int mTikeCount;
    private Context mContext;

    public MyPanelView(Context context) {
        super(context);
    }

    public MyPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyPanelView);
        mArcColor = a.getColor(R.styleable.MyPanelView_arcColor, Color.parseColor("#5fb1b"));
        mMinCircleColor = a.getColor(R.styleable.MyPanelView_pointerColor,Color.parseColor("#5fb1b"));
        mTikeCount = a.getInteger(R.styleable.MyPanelView_tikeCount, 12);
        mTextSize = a.getDimensionPixelSize(R.styleable.MyPanelView_android_textSize, 15);
        mText = a.getString(R.styleable.MyPanelView_android_text);
        mScendArcWidth = 50;
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec ){
        mWidth = getDefaultSize(200,widthMeasureSpec);
        mHeight = getDefaultSize(200,heightMeasureSpec);
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    public void onDraw(Canvas canvas){
        Paint p = new Paint();
    }
}
