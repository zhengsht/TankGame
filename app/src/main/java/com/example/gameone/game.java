package com.example.gameone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class game extends Activity {
    final int CRASH = 0;
    AquaBlue a,b;
    Corona corona1, corona2;
    float mR = (float) 0.8;
    boolean isGameOver = false;
    boolean getmessageFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backgroun);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        init();
    }


    private void init(){
        a = findViewById(R.id.aquaBlue);
        a.setXY(500,100);
        a.setColor(231, 64, 50);
        b = findViewById(R.id.aquaBlue2);
        b.setXY(500,1000);
        b.setColor(33, 145, 65);
        corona1 = findViewById(R.id.corona1);
        corona2 = findViewById(R.id.corona2);
        corona1.setOnMyCoronaMoveListener(new Corona.OnMyCoronaMoveListener() {
            @Override
            public void onTouched(float angle) {
                if(!isGameOver){
                    a = findViewById(R.id.aquaBlue);
                    b = findViewById(R.id.aquaBlue2);
                    if((a.x + 5*Math.cos(angle))<a.getWidth() && (a.x + mR*Math.cos(angle))>0){
                        a.x = (float) (a.x + mR*Math.cos(angle));
                    }
                    if((a.y + 5*Math.sin(angle))<a.getHeight() && (a.y + mR*Math.sin(angle))>0){
                        a.y = (float) (a.y + mR*Math.sin(angle));
                    }
                    a.radia = angle;
                    a.invalidate();
                    if(getDistance(a.x,a.y,b.x,b.y) <= 70 && !corona2.isTouch && getmessageFlag){
                        getmessageFlag = false;
                        System.out.println("sendmessage1");
                        Message msg1 = new Message();
                        msg1.what = CRASH;
                        mhandler.sendMessage(msg1);
                    }
                    System.out.println(getDistance(a.x,a.y,b.x,b.y)+"--"+corona2.isTouch+getmessageFlag);
                }
            }
        });
        corona2.setOnMyCoronaMoveListener(new Corona.OnMyCoronaMoveListener() {
            @Override
            public void onTouched(float angle) {
                if(!isGameOver){
                    a = findViewById(R.id.aquaBlue);
                    b = findViewById(R.id.aquaBlue2);
                    if((b.x + 5*Math.cos(angle))<b.getWidth() && (b.x + mR*Math.cos(angle))>0){
                        b.x = (float) (b.x + mR*Math.cos(angle));
                    }
                    if((b.y + 5*Math.sin(angle))<b.getHeight() && (b.y + mR*Math.sin(angle))>0){
                        b.y = (float) (b.y + mR*Math.sin(angle));
                    }
                    b.radia = angle;
                    b.invalidate();
                    if(getDistance(a.x,a.y,b.x,b.y) <= 70 && getmessageFlag){
                        getmessageFlag = false;
                        System.out.println("sendmessage2");
                        Message msg2 = new Message();
                        msg2.what = CRASH;
                        mhandler.sendMessage(msg2);
                    }
                    System.out.println(getDistance(a.x,a.y,b.x,b.y)+"--"+corona2.isTouch+getmessageFlag);
                }
            }
        });
    }


    public float getDistance(float x1, float y1, float x2, float y2){
        return (float) Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    @Override
    protected void onResume() {
        overridePendingTransition(0, 0);
        super.onResume();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    Handler mhandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case CRASH:
                    System.out.println("getmessage");
                    try{
                        corona1 = findViewById(R.id.corona1);
                        corona2 = findViewById(R.id.corona2);
                        corona1.t.interrupt();
                        corona2.t.interrupt();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    isGameOver = true;
                    setContentView(R.layout.gameover);
                    getmessageFlag = true;
                    break;
            }
            return false;
        }
    });

    public void back(View view) {
        finish();
        overridePendingTransition(0,0);
    }
}
