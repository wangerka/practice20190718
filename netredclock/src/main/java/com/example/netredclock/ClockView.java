package com.example.netredclock;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.Calendar;

public class ClockView extends View {
    private Paint paint;

    private float mHDegreen=0;
    private float mMDegreen=0;
    private float mSDegreen=0;

    private ValueAnimator animator;

    public ClockView(Context context) {
        this(context,null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        paint.setTextSize(32);
        paint.setAntiAlias(true);

        animator = ValueAnimator.ofFloat(6,0);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        canvas.translate(getWidth()/2, getHeight()/2);
        drawTime(canvas);
        drawHours(canvas,mHDegreen);
        drawMinutes(canvas,mMDegreen);
        drawSeconds(canvas,mSDegreen);

        canvas.restore();
    }

    private void drawTime(Canvas canvas) {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int week = c.get(Calendar.DAY_OF_WEEK);
        int minute = c.get(Calendar.MINUTE);
        canvas.drawText(month+"月"+day+"日\n",-50,0,paint);
        canvas.drawText(hour+"点"+minute+"分"+Util.getWeek(week),-100,30,paint);
    }

    private void drawHours(Canvas canvas,float degreen) {
        canvas.save();
        canvas.rotate(degreen);
        for(int i=0;i<12;i++){
            canvas.save();
            float d = 360/12*i;
            canvas.rotate(d);
            paint.setAlpha((d+degreen) ==0?255:100);
            canvas.drawText((i+1)+"点",getWidth()*0.15f,0,paint);
            canvas.restore();
        }
        canvas.restore();
    }

    private void drawMinutes(Canvas canvas,float degreen){
        canvas.save();
        canvas.rotate(degreen);
        for(int i=0;i<60;i++){
            canvas.save();
            float d = 360/60*i;
            canvas.rotate(d);
            paint.setAlpha((d+degreen) ==0?255:100);
            canvas.drawText(i+"分",getWidth()*0.25f,0,paint);
            canvas.restore();
        }
        canvas.restore();
    }

    private void drawSeconds(Canvas canvas,float degreen){
        canvas.save();
        canvas.rotate(degreen);
        for(int i=0;i<60;i++){
            canvas.save();
            float d = 360/60*i;
            canvas.rotate(d);
            paint.setAlpha((d+degreen) ==0?255:100);
            canvas.drawText(i+"秒",getWidth()*0.32f,0,paint);
            canvas.restore();
        }
        canvas.restore();
    }

    public void updateClock(){
        Calendar c = Calendar.getInstance();
        final int h = c.get(Calendar.HOUR_OF_DAY);
        final int m = c.get(Calendar.MINUTE);
        final int s = c.get(Calendar.SECOND);

        mHDegreen = -360/12*(h-1);
        mMDegreen = -360/60*m;
        mSDegreen = -360/60*s;

        final float currentH = mHDegreen;
        final float currentM = mMDegreen;
        final float currentS = mSDegreen;

        animator.removeAllUpdateListeners();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float v = (float)valueAnimator.getAnimatedValue();
                if(m==0 && s ==0) {
                    mHDegreen = currentH + v * 5;
                }
                if(s==0) {
                    mMDegreen = currentM + v;
                }
                mSDegreen = currentS+v;

                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

        invalidate();
    }

}
