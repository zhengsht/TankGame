package com.example.gameone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Corona extends View implements View.OnTouchListener {
    private Paint mPaint;
    public boolean isTouch = false;

    public boolean isLeft = true;
    public float angle = 0;
    int centerX;//方向盘X轴中心
    int centerY;//方向盘Y轴中心
    float positionX;
    float positionY;
    OnMyCoronaMoveListener myCoronaMoveListener;
    Thread t;

    public Corona(Context context){
        super(context);
        init();
    }
    public Corona(Context context, AttributeSet attr){
        super(context,attr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.purple_200));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(36);
        mPaint.setStrokeWidth(20);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        float R = canvas.getWidth()/6;
        RectF myrect = new RectF(-R, -R, R, R);
        canvas.translate(canvas.getWidth()/2, canvas.getHeight()/2);//原点移至画布中心
        canvas.drawArc(myrect, 0, 360, false, mPaint);//画表盘圆周阴影

        if(isTouch){
            canvas.save();
            canvas.translate((float) ((R-30)*Math.cos(angle)),
                    (float) ((R-30)*Math.sin(angle)));

            canvas.drawArc(new RectF(-25,-25,25,25),
                    0, 360, false, mPaint);//画表盘圆周阴影
            canvas.restore();
        }else {
            canvas.drawArc(new RectF(-25,-25,25,25),
                    0, 360, false, mPaint);//画表盘圆周阴影
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width =MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.UNSPECIFIED?100:MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.UNSPECIFIED?100:MeasureSpec.getSize(heightMeasureSpec);
        if(width>height){//将自定义控件的区域限制为正方形
            width=height;
        }else{
            height=width;
        }
        setMeasuredDimension((int) (width*0.5), (int) (height*0.5));
        this.centerX = (int) (width*0.25);
        this.centerY = (int) (height*0.25);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            System.out.println("222222222222222222");
            positionX = event.getX();
            positionY = event.getY();
            isTouch = true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            System.out.println("66666666666666");
            isTouch = false;
            invalidate();
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            System.out.println("333333333333333333");
            positionX = event.getX();
            positionY = event.getY();
            isTouch = true;
        }
        if(isLeft){
            isLeft = false;
            t = new Thread() {
                @Override
                public void run() {
                    System.out.println("777777777777");
                    while (isTouch) {
                        try{
                            System.out.println("444444444444444444");
                            angle = (float) Math.atan2((positionY-centerY),
                                    (positionX-centerX));
                            if(myCoronaMoveListener!=null){
                                myCoronaMoveListener.onTouched(angle);
                            }
                            invalidate();
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                            break;
                        }
                    }
                    isLeft = true;
                    System.out.println("55555555555555555555");
                }
            };
            t.start();
        }
        return true;
    }

    public void setOnMyCoronaMoveListener(OnMyCoronaMoveListener listener){
        this.myCoronaMoveListener=listener;
    }

    public void removeOnMyCoronaMoveListener(OnMyCoronaMoveListener listener) {
        removeOnMyCoronaMoveListener(listener);
    }

    public static abstract interface OnMyCoronaMoveListener {
        public abstract void onTouched(float angle);
    }
}
