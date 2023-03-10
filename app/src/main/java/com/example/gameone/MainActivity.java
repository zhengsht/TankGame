package com.example.gameone;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

public class MainActivity extends Activity {
    Display window;
    Point win = new Point();
    AquaBlue a,b;
    Corona corona;
    float mR = (float) 0.8;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backgroun);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        b =findViewById(R.id.aquaBlue2);
        b.setXY(50,50);
        window = getWindowManager().getDefaultDisplay();
        window.getSize(win);
        corona = findViewById(R.id.corona1);
        corona.setOnMyCoronaMoveListener(new Corona.OnMyCoronaMoveListener() {
            @Override
            public void onTouched(float angle) {
                a = findViewById(R.id.aquaBlue);
                if((a.x + 5*Math.cos(angle))<win.x && (a.x + mR*Math.cos(angle))>0){
                    a.x = (float) (a.x + mR*Math.cos(angle));
                }
                if((a.y + 5*Math.sin(angle))<win.y && (a.y + mR*Math.sin(angle))>0){
                    a.y = (float) (a.y + mR*Math.sin(angle));
                }
                a.invalidate();
            }
        });
    }
}
