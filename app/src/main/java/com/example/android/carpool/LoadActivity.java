package com.example.android.carpool;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class LoadActivity extends AppCompatActivity {
    AppCompatImageView car;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        car=findViewById(R.id.car);

        bounceOff();

    }

    public void bounceOff(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        car.startAnimation(animation);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                off();
            }
        }, 5000);

    }

    public void off(){
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_right);
        car.startAnimation(animation1);
        final MediaPlayer mp =MediaPlayer.create(this, R.raw.carstartgarage1);
        mp.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 5000);
    }


}
