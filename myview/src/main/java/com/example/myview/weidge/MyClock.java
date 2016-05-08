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
 * Created by Administrator on 2016/5/5.
 */
public class MyClock extends View {

    private int width;
    private int height;
    private Paint mPaintLine;
    private Paint mPaintCircle;
    private Paint mPaintHour;
    private Paint mPaintminute;
    private Paint mPaintSes;
    private Paint mPaintText;
    private Calendar mCalendar;
    public static final int NEED_INVALIDATE = 0x23;
    //每隔一秒，在handler中调用一次重新绘制方法
    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case NEED_INVALIDATE:
                    mCalendar = Calendar.getInstance();
                    invalidate();//告诉主线程重新绘制
                    mhandler.sendEmptyMessageDelayed(NEED_INVALIDATE,1000);
                    break;
                default:
                    break;
            }
        }
    };

    public MyClock(Context context) {
        super(context);
    }

    public MyClock(Context context, AttributeSet attrs) {
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

        mhandler.sendEmptyMessage(NEED_INVALIDATE);//向handler发送一个消息，让它开启重绘
    }

    public MyClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        Log.e("MyClock","onMeasure");
        Log.e("MyClock","widthMeasureSpec:"+widthMeasureSpec+"   heightMeasureSpec:"+heightMeasureSpec);
        int mode1 = MeasureSpec.getMode(widthMeasureSpec);
        int mode2 = MeasureSpec.getMode(heightMeasureSpec);
        if(mode1==MeasureSpec.AT_MOST){
            Log.e("MyClock","width  AT_MOST");
        }
        if(mode1==MeasureSpec.EXACTLY){
            Log.e("MyClock","width  EXACTLY");
        }

        if(mode2 ==MeasureSpec.AT_MOST){
            Log.e("MyClock","height  AT_MOST");
        }
        if(mode2==MeasureSpec.EXACTLY){
            Log.e("MyClock","height  EXACTLY");
        }
        if(mode2==MeasureSpec.UNSPECIFIED){
            Log.e("MyClock","height  UNSPECIFIED");
        }


        width = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
        Log.e("MyClock","width:"+width+"    height:"+height);
        setMeasuredDimension(width, height);
    }

    @Override
    public void onDraw(Canvas canvas){
        Log.e("MyClock","onDraw");
        int circleRadius = 300;//大圆直径
        canvas.drawCircle(width/2,height/2,circleRadius,mPaintCircle);
        //画出圆中心
        canvas.drawCircle(width / 2, height / 2, 20, mPaintCircle);
        //依次旋转画布，画出每隔时刻和对应的数字
        for(int i = 1; i<=12 ; i++){
            canvas.save();
            //每次旋转360/12*i度，旋转中心坐标是（width/2,height/2）
            canvas.rotate(360/12*i,width/2,height/2);
            //左起：起始位置x坐标，起始位置y坐标，终止位置x坐标，终止位置y坐标
            canvas.drawLine(width / 2, height / 2 - circleRadius, width / 2, height / 2 - circleRadius + 30,mPaintLine);
            //文本的内容，起始位置x坐标，起始位置y坐标，画笔
            canvas.drawText(""+i,width / 2, height / 2 - circleRadius + 70,mPaintText);
            canvas.restore();
        }

        int minute = mCalendar.get(Calendar.MINUTE);//得到当前的分钟数
        int hour = mCalendar.get(Calendar.HOUR);//得到当前的小时；
        int sec = mCalendar.get(Calendar.SECOND);//得到当前的分钟数

        float minuteDegree = minute/60f*360;//得到分针旋转的角度
        canvas.save();
        canvas.rotate(minuteDegree, width / 2, height / 2);
        canvas.drawLine(width / 2, height / 2 - 50, width / 2, height / 2 + 40, mPaintminute);
        canvas.restore();

        float hourDegree = (hour*60+minute)/12f/60*360;//得到时钟旋转的角度
        canvas.save();
        canvas.rotate(hourDegree, width / 2, height / 2);
        canvas.drawLine(width / 2, height / 2 - 100, width / 2, height / 2 + 30, mPaintHour);
        canvas.restore();

        float secDegree = sec/60f*360;//得到秒针旋转的角度
        canvas.save();
        canvas.rotate(secDegree,width/2,height/2);
        canvas.drawLine(width/2,height/2-150,width/2,height/2+40,mPaintSes);
        canvas.restore();
    }
}
