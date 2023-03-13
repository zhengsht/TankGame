package com.example.gameone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class game extends Activity {
    final int CRASH = 0;
    final int FLASH = 1;
    final int QUICKEN = 2;

    AquaBlue a,b;
    Corona corona1, corona2;
    float mR = (float) 0.8;
    float mR2 = (float) 0.8;
    boolean isGameOver = false;
    boolean getmessageFlag = true;

    boolean isFlashOnCD = false;
    boolean isQuickenOnCD = false;

    static int flashDistance = 200;
    static int quickTime = 500;//ms

    static int flashCD = 5;
    int CD_flash = flashCD;
    static int quickenCD = 3;
    int CD_quicken = quickenCD;

    Thread mT1;
    Thread mT2;
    Thread mT3;
    long startTime;
    long currentTime;

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
                    if((a.x + mR2*Math.cos(angle))<a.getWidth() && (a.x + mR2*Math.cos(angle))>0){
                        a.x = (float) (a.x + mR2*Math.cos(angle));
                    }
                    if((a.y + mR2*Math.sin(angle))<a.getHeight() && (a.y + mR2*Math.sin(angle))>0){
                        a.y = (float) (a.y + mR2*Math.sin(angle));
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
                    if((b.x + mR*Math.cos(angle))<b.getWidth() && (b.x + mR*Math.cos(angle))>0){
                        b.x = (float) (b.x + mR*Math.cos(angle));
                    }
                    if((b.y + mR*Math.sin(angle))<b.getHeight() && (b.y + mR*Math.sin(angle))>0){
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
                case FLASH:
                    try{
                        Button flash = findViewById(R.id.button4);
                        if(message.arg1 == 0){
                            flash.setText("FLASH");
                        }else {
                            flash.setText(""+message.arg1);
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    break;

                case QUICKEN:
                    try{
                        Button quick = findViewById(R.id.button5);
                        if(message.arg1 == 0){
                            quick.setText("QUICKEN");
                        }else {
                            quick.setText(""+message.arg1);
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    break;
            }
            return false;
        }
    });

    public void back(View view) {
        finish();
        overridePendingTransition(0,0);
    }

    public void quicken(View view) {
        if(!isQuickenOnCD){
            isQuickenOnCD = true;
            mT1 = new Thread() {
                @Override
                public void run() {
                    while (isQuickenOnCD) {
                        try{
                            Message quickenMsg = new Message();
                            quickenMsg.what = QUICKEN;
                            quickenMsg.arg1 = CD_quicken;
                            mhandler.sendMessage(quickenMsg);
                            CD_quicken = CD_quicken - 1;
                            Thread.sleep(1000);
                            if(CD_quicken == 0){
                                isQuickenOnCD = false;
                            }
                        }catch (InterruptedException e){
                            e.printStackTrace();
                            break;
                        }
                    }
                    Message flashMsg = new Message();
                    flashMsg.what = QUICKEN;
                    flashMsg.arg1 = CD_quicken;
                    mhandler.sendMessage(flashMsg);
                    CD_quicken = quickenCD;
                }
            };
            mT1.start();

            mT2 = new Thread() {
                @Override
                public void run() {
                    mR2 = (float) 1.5 * mR2;
                    int cnt = quickTime;
                    while (cnt != 0) {
                        try{
                            cnt = cnt - 500;
                            Thread.sleep(500);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                            break;
                        }
                    }
                    mR2 = (float) (mR2 / 1.5);
                }
            };
            mT2.start();
        }
    }

    public void flash(View view) {
        if(!isFlashOnCD){
            isFlashOnCD = true;
            mT3 = new Thread() {
                @Override
                public void run() {
                    while (isFlashOnCD) {
                        try{
                            Message flashMsg = new Message();
                            flashMsg.what = FLASH;
                            flashMsg.arg1 = CD_flash;
                            mhandler.sendMessage(flashMsg);
                            CD_flash = CD_flash - 1;
                            Thread.sleep(1000);
                            if(CD_flash == 0){
                                isFlashOnCD = false;
                            }
                        }catch (InterruptedException e){
                            e.printStackTrace();
                            break;
                        }
                    }
                    Message flashMsg = new Message();
                    flashMsg.what = FLASH;
                    flashMsg.arg1 = CD_flash;
                    mhandler.sendMessage(flashMsg);
                    CD_flash = flashCD;
                }
            };
            mT3.start();
            b = findViewById(R.id.aquaBlue2);
            if((b.x + flashDistance*mR*Math.cos(b.radia))<b.getWidth() && (b.x + flashDistance*mR*Math.cos(b.radia))>0){
                b.x = (float) (b.x + flashDistance*mR*Math.cos(b.radia));
            }
            if((b.y + flashDistance*mR*Math.sin(b.radia))<b.getHeight() && (b.y + flashDistance*mR*Math.sin(b.radia))>0){
                b.y = (float) (b.y + flashDistance*mR*Math.sin(b.radia));
            }
            b.invalidate();
        }
    }
}
