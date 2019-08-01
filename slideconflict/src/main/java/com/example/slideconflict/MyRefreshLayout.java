package com.example.slideconflict;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

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
        int startX=0;
        int startY=0;
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int distancdX = Math.abs((int) ev.getX() - startX);
                int distancdY = Math.abs((int) ev.getY() - startY);
                if(distancdX > mTouchSlop && distancdX > distancdY){
                    return false;
                }
                break;
        }


        return super.onInterceptTouchEvent(ev);
    }
}
