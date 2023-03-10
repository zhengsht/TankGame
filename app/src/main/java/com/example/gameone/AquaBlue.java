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
        x = 100;
        y = 150;
    }

    public void setXY(float x, float y){
        this.x = x;
        this.y = y;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float R = 10;
        RectF myrect = new RectF(-R, -R, R, R);
        canvas.translate(x, y);//原点移至画布中心
        canvas.save();//保存中心位置
        canvas.translate(5, 5);
        canvas.save();//保存阴影偏移位置
        canvas.drawRect(myrect, mPaint);//画表盘圆周阴影
    }
}
