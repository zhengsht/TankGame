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
    Point p = new Point();
    Display w;
    AquaBlue a,b;
    Corona corona;
    final int press = 0;
    final int measure = 2;
    long downTime = 0;//Button被按下时的时间
    long thisTime = 0;//while每次循环时的时间
    boolean onBtnTouch = false;//Button是否被按下
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
        w  = getWindowManager().getDefaultDisplay();
        w.getSize(p);
        corona = findViewById(R.id.corona1);
        corona.setOnMyCoronaMoveListener(new Corona.OnMyCoronaMoveListener() {
            @Override
            public void onTouched(float angle) {
                System.out.println("chufalejiant");
                a = findViewById(R.id.aquaBlue);
                a.x = (float) (a.x + 5*Math.cos(angle));
                a.y = (float) (a.y + 5*Math.sin(angle));
                a.invalidate();
            }
        });
    }

    private Handler mhandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
           switch (message.what) {
               case press:
                   break;
            }
            return false;
        }
    });
}
