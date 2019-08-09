package com.example.netredclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

public class ClockView extends View {
    private Paint paint;

    private float mHDegreen=0;
    private float mMDegreen=0;
    private float mSDegreen=0;

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
        canvas.drawText(hour+"点"+minute+"分"+Util.getWeek(week),-50,30,paint);
    }

    private void drawHours(Canvas canvas,float degreen) {
        canvas.save();
        canvas.rotate(degreen);
        for(int i=1;i<=12;i++){
            canvas.save();
            canvas.rotate(360/12*(i-1));
            canvas.drawText(i+"点",getWidth()*0.15f,0,paint);
            canvas.restore();
        }
        canvas.restore();
    }

    private void drawMinutes(Canvas canvas,float degreen){
        canvas.save();
        canvas.rotate(degreen);
        for(int i=0;i<60;i++){
            canvas.save();
            canvas.rotate(360/60*i);
            if(i != 0) canvas.drawText(i+"分",getWidth()*0.25f,0,paint);
            canvas.restore();
        }
        canvas.restore();
    }

    private void drawSeconds(Canvas canvas,float degreen){
        canvas.save();
        canvas.rotate(degreen);
        for(int i=0;i<60;i++){
            canvas.save();
            canvas.rotate(360/60*i);
            if(i!=0) canvas.drawText(i+"秒",getWidth()*0.32f,0,paint);
            canvas.restore();
        }
        canvas.restore();
    }

    public void updateClock(){
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        int s = c.get(Calendar.SECOND);

        mHDegreen = -360/12*(h-1);
        mMDegreen = -360/60*m;
        mSDegreen = -360/60*s;

        invalidate();
    }

}
