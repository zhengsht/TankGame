package com.example.gameone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class AquaBlue extends View {
    private Paint mPaint;
    float x,y;
    float radia;

    public Paint tempPaint;//刻度阴影画笔

    public AquaBlue(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.purple_200));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(36);
        mPaint.setStrokeWidth(20);
        tempPaint = new Paint(mPaint);
        x = 100;
        y = 150;
        radia = 0;
    }

    public void setXY(float x, float y){
        this.x = x;
        this.y = y;
        invalidate();
    }

    public void setColor(int r, int g, int b){
        tempPaint.setARGB(255,r,g,b);
    }

    private float radia2angle(float radia){
        return (float) ((radia*180)/Math.PI);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float R = 10;
        RectF myrect = new RectF(-2*R, -R, 2*R, R);
        canvas.translate(x, y);//原点移至画布中心
        canvas.save();//保存中心位置
        canvas.rotate(radia2angle(radia));
        canvas.drawRect(myrect, mPaint);//画表盘圆周阴影
        canvas.drawRect(new RectF((float) 1.95*R,-R,2*R,R), tempPaint);//画表盘圆周阴影
        canvas.restore();
    }
}
