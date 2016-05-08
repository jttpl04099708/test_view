package com.example.myview;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class ViewTestActivity extends Activity implements View.OnClickListener{
    ImageView mImage1;
    ImageView mImage2;
    ImageView mImage3;
    Button mBtnShuZhi;
    Button mBtnPaoWu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_clock);
        mImage1 = (ImageView)findViewById(R.id.iv_qie1);
        mImage2 = (ImageView)findViewById(R.id.iv_qie2);
        mImage3 = (ImageView)findViewById(R.id.iv_qie3);
        mBtnShuZhi = (Button)findViewById(R.id.btn_shuzhi);
        mBtnPaoWu = (Button)findViewById(R.id.btn_paowu);
        mBtnShuZhi.setOnClickListener(this);
        mBtnPaoWu.setOnClickListener(this);
    }

    //使用ObjectAnimator实现动画
    public void rotateyAnimRun1(View v){
        //沿着x轴的中心 旋转360度
        ObjectAnimator.ofFloat(v,"rotationX",0.0f,360.0f).setDuration(5000).start();
    }

    //既能缩小 又能淡出   同事更改多个属性
    public void rotateyAnimRun2(View v){
        //沿着x轴的中心 旋转360度
        animationScale(v);
    }
    //使用ObjectAnimator 同时更改多个属性  方法1
    public void  animationScale(final View v){
        ObjectAnimator anim = ObjectAnimator.ofFloat(v,"zhy",1.0f,0.0f).setDuration(5000);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                v.setAlpha(cVal);
                v.setScaleX(cVal);
                v.setScaleY(cVal);
                //v.setTranslationX((1 - cVal) * 300);//向左移, 位置坐标发生变化,,但是焦点没有发生变化
                // v.setPadding((int) ((1 - cVal) * 500), 0, 0, 0);//向右移 但是坐标仍然不变
            }
        });
    }
    //使用

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MyClockActivity", mImage2.getX() + "平移后的x坐标");
    }

    @Override
    public void onClick(View v) {
        if(v==mBtnShuZhi){
            ziyouAnimation(mImage3);
        }
        if(v==mBtnPaoWu){
            paowuAnimator(mImage3);
        }
    }

    //自由落体动画
    public void ziyouAnimation(final View v){
        mImage3.setX(0);
        final ValueAnimator animator = ValueAnimator.ofFloat(0,1000-v.getHeight());//1200是外层布局的高
        animator.setTarget(v);
        animator.setDuration(1000).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cval = (float)animator.getAnimatedValue();
                v.setTranslationY(cval);
            }
        });
    }

    //抛物线运动
    public void paowuAnimator(View v){
        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(3000);
        animator.setObjectValues(new Point(0, 0));
        animator.setInterpolator(new LinearInterpolator());
        animator.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                Log.e("MyClockActivity","fraction:"+fraction);
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                mImage3.setX(point.x);
                mImage3.setY(point.y);
            }
        });
    }
}
