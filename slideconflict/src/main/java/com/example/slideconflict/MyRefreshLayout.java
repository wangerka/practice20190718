package com.example.slideconflict;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.util.Log;

public class MyRefreshLayout extends SwipeRefreshLayout {

    private int mTouchSlop;

    public MyRefreshLayout(@NonNull Context context) {
        this(context,null);
    }

    public MyRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float startX=0;
        float startY=0;
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distancdX = Math.abs(ev.getX() - startX);
                float distancdY = Math.abs(ev.getY() - startY);
                if(distancdX > mTouchSlop && distancdX > distancdY){
                    Log.i("gejun","move move move!!!!!");
                    return false;
                }
                break;
        }


        return super.onInterceptTouchEvent(ev);
    }
}
