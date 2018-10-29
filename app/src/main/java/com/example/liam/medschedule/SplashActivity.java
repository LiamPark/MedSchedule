package com.example.liam.medschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    //splash Activity Image view is initialized
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView iv = (ImageView) findViewById(R.id.splashLogo);

        //after 5s intent to main screen
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(SplashActivity.this, ScheduleActivity.class);
                    startActivity(i);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
