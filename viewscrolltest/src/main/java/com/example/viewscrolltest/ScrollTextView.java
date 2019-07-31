package com.example.viewscrolltest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Scroller;
import android.widget.TextView;

public class ScrollTextView extends TextView {
    Scroller scroller;
    public ScrollTextView(Context context) {
        this(context,null);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    public void smoothScroll(int destX, int destY){
        int startX = getScrollX();
        int startY = getScrollY();
        int dx = destX -startX;
        int dy=destY-startY;
        scroller.startScroll(startX, startY,dx,dy,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
//            invalidate();
        }
    }
}
