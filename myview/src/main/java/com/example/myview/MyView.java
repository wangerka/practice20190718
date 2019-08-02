package com.example.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    private int mDefaultWidth=100;
    private int mDefaultHeight=100;

    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawCircle(getWidth()/2,getHeight()/2,30,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heigh = MeasureSpec.getSize(heightMeasureSpec);
        if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mDefaultWidth, mDefaultHeight);
        } else if(widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mDefaultWidth, heigh);
        } else if(heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(width, mDefaultHeight);
        }

    }
}
