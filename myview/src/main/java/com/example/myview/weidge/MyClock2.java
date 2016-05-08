package com.example.myview.weidge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/5/6.
 */
public class MyClock2 extends View {
    private int width;
    private int height;
    private Paint mPaintLine;
    private Paint mPaintCircle;
    private Paint mPaintHour;
    private Paint mPaintminute;
    private Paint mPaintSes;
    private Paint mPaintText;
    private Calendar mCalendar;
    public static final int NEED_INVALIDATE2 = 0x24;

    //每隔一秒，在handler中调用一次重新绘制方法
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case NEED_INVALIDATE2:
                    mCalendar = Calendar.getInstance();
                    invalidate();//告诉主线程重新绘制
                    handler.sendEmptyMessageDelayed(NEED_INVALIDATE2,1000);
                    break;
                default:
                    break;
            }
        }
    };

    public MyClock2(Context context) {
        super(context);
    }

    public MyClock2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCalendar = Calendar.getInstance();

        //设置画笔
        mPaintLine = new Paint();
        mPaintLine.setColor(Color.BLUE);
        mPaintLine.setStrokeWidth(10);

        mPaintCircle = new Paint();
        mPaintCircle.setColor(Color.GREEN);
        mPaintCircle.setStrokeWidth(10);
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStyle(Paint.Style.STROKE);//设置绘制风格  空心圆

        mPaintText = new Paint();
        mPaintText.setColor(Color.BLUE);
        mPaintText.setStrokeWidth(10);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(40);

        mPaintHour = new Paint();
        mPaintHour.setStrokeWidth(20);
        mPaintHour.setColor(Color.BLUE);

        mPaintminute = new Paint();
        mPaintminute.setStrokeWidth(15);
        mPaintminute.setColor(Color.BLUE);

        mPaintSes = new Paint();
        mPaintSes.setStrokeWidth(10);
        mPaintSes.setColor(Color.BLUE);
        handler.sendEmptyMessage(NEED_INVALIDATE2);
    }

    public MyClock2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        width = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
        Log.e("MyClock", "width:" + width + "    height:" + height);
        setMeasuredDimension(width, height);
    }
    @Override
    public void onDraw(Canvas canvas){
        drawClockPanel(canvas);
        drawPointer(canvas);
    }

    public void drawClockPanel(Canvas canvas){
        int circleRadius = 300;//大圆直径
        canvas.drawCircle(width/2,height/2,circleRadius,mPaintCircle);
        //画出圆中心
        canvas.drawCircle(width / 2, height / 2, 20, mPaintCircle);
        //画钟表的刻度
        for(int i=1;i<=12;i++) {
            float startX = (float) (width/2+(circleRadius-20)* Math.sin(30 * i / 180f * Math.PI));
            float startY = (float) ( height/2-(circleRadius-20)*Math.cos(30 * i / 180f * Math.PI));
            float endX = (float) ( width/2+circleRadius*Math.sin(30 * i / 180f * Math.PI));
            float endY = (float) ( height/2-circleRadius*Math.cos(30 * i / 180f * Math.PI));
            canvas.drawLine(startX, startY, endX, endY, mPaintLine);

            startX = (float) (width/2+(circleRadius-50)* Math.sin(30 * i / 180f * Math.PI));
            startY = (float) ( height/2-(circleRadius-50)*Math.cos(30 * i / 180f * Math.PI));
            canvas.drawText("" + i, startX, startY, mPaintText);
        }
    }

    public void drawPointer(Canvas canvas){
        //获得当前的时
        int hour = mCalendar.get(Calendar.HOUR);
        //计算当前的小时所对应的角度
        float hourAngle = hour/12f*360;
        float startX = (float)(width/2-30* Math.sin(hourAngle / 180f * Math.PI));
        float startY = (float) ( height/2+30*Math.cos(hourAngle / 180f * Math.PI));
        float endX = (float) ( width/2+50*Math.sin(hourAngle / 180f * Math.PI));
        float endY = (float) ( height/2-50*Math.cos(hourAngle / 180f * Math.PI));
        canvas.drawLine(startX, startY, endX, endY, mPaintHour);

        //获得当前的分钟
        int minite = mCalendar.get(Calendar.MINUTE);
        //计算当前的分钟所对应的角度
        float miniteAngle = minite/60f*360;
         startX = (float)(width/2-30* Math.sin(miniteAngle / 180f * Math.PI));
         startY = (float) ( height/2+30*Math.cos(miniteAngle / 180f * Math.PI));
         endX = (float) ( width/2+100*Math.sin(miniteAngle / 180f * Math.PI));
         endY = (float) ( height/2-100*Math.cos(miniteAngle / 180f * Math.PI));
        canvas.drawLine(startX, startY, endX, endY, mPaintminute);

        //获得当前的秒
        int sec = mCalendar.get(Calendar.SECOND);
        //计算当前的分钟所对应的角度
        float secAngle = sec / 60f * 360;
        startX = (float)(width/2-30* Math.sin(secAngle / 180f * Math.PI));
        startY = (float) ( height/2+30*Math.cos(secAngle / 180f * Math.PI));
        endX = (float) ( width/2+150*Math.sin(secAngle / 180f * Math.PI));
        endY = (float) ( height/2-150*Math.cos(secAngle / 180f * Math.PI));
        canvas.drawLine(startX, startY, endX, endY, mPaintSes);
    }
}
