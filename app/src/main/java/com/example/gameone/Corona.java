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
    private boolean isTouch = false;
    public float angle = 0;
    int centerX;//方向盘X轴中心
    int centerY;//方向盘Y轴中心
    OnMyCoronaMoveListener myCoronaMoveListener;

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
        setMeasuredDimension((int) (width*0.8), (int) (height*0.8));
        this.centerX = (int) (width*0.4);
        this.centerY = (int) (height*0.4);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        isTouch = true;
        angle = (float) Math.atan2((event.getY()-this.centerY),(event.getX()-this.centerX));
        if(this.myCoronaMoveListener!=null){
            this.myCoronaMoveListener.onTouched(this.angle);
        }
        invalidate();
        if(event.getAction() == 1){
            isTouch = false;
            if(this.myCoronaMoveListener!=null){
                this.myCoronaMoveListener.onTouched(this.angle);
            }
        }
        invalidate();
        return true;
    }

    public void setOnMyCoronaMoveListener(OnMyCoronaMoveListener listener){
        this.myCoronaMoveListener=listener;
    }

    public static abstract interface OnMyCoronaMoveListener {
        public abstract void onTouched(float angle);
    }
}
