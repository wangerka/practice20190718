package com.example.tuozhuailist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class TuoZhuaiListView extends ListView {
    private Context context;
    WindowManager wm;
    WindowManager.LayoutParams mLayoutParams;

    public TuoZhuaiListView(Context context) {
        this(context, null);
    }

    public TuoZhuaiListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TuoZhuaiListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    int x1;
    int y1;
    int x,y,rawX,rawY,left,top,right,bottom;
    ImageView image;
    int pointx,pointy,screenx,screeny;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) ev.getX();
                y = (int) ev.getY();
                rawX = (int)ev.getRawX();
                rawY = (int)ev.getRawY();
                int num = pointToPosition(x, y);
                if(num == AdapterView.INVALID_POSITION){
                    break;
                }
                Log.i("gejun", "x = " + x+",y = "+y+",rawX ="+rawX+", rawY = "+rawY);

                final View item = getChildAt(num - getFirstVisiblePosition());
                left = item.getLeft();
                top = item.getTop();
                right= item.getRight();
                bottom=item.getBottom();
                pointx=x-left;
                pointy=y-top;
                screenx=rawX-x;
                screeny=rawY-y;
                ((View) item).setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        item.setDrawingCacheEnabled(true);
                        Bitmap bitmap = item.getDrawingCache();
                        startDraging(bitmap);
                        return true;
                    }
                });
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    public void startDraging(Bitmap bm) {
        stopDraging();

        mLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        mLayoutParams.x = rawX - x;
        mLayoutParams.y = rawY - y + top;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mLayoutParams.windowAnimations = 0;

        ImageView iv = new ImageView(context);
        iv.setImageBitmap(bm);
        iv.setBackgroundColor(Color.RED);
        image = iv;

        wm.addView(iv, mLayoutParams);
    }

    public void stopDraging(){
        if(image != null){
            wm.removeView(image);
            image=null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_UP:
                stopDraging();
                break;
            case MotionEvent.ACTION_MOVE:
                if(image != null){
                    int x = (int) ev.getX();
                    int y = (int) ev.getY();
                    mLayoutParams.x=left;
                    mLayoutParams.y=y-pointy+screeny;
                    if(mLayoutParams.y <screeny){
                        mLayoutParams.y = screeny;
                    }
                    Log.i("gejun","listview bottom = "+getBottom());
                    Log.i("gejun","y = "+mLayoutParams.y);
                    if(mLayoutParams.y>getBottom()-(bottom-top)+screeny){
                        mLayoutParams.y=getBottom()-(bottom-top)+screeny;
                    }
                    wm.updateViewLayout(image, mLayoutParams);
                    return true;
                }

        }
        return super.onTouchEvent(ev);
    }
}
