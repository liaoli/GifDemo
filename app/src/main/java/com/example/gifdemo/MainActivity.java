package com.example.gifdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    private GifDrawable gifFromAssets;
    private GifImageView gifImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gifImageView = findViewById(R.id.gif_view);
    }

    public void startAnimation(View view) {
        if (gifFromAssets != null && gifFromAssets.isPlaying()) {
            return;
        }

        if (gifFromAssets == null) {
            try {
                gifFromAssets = new GifDrawable(getAssets(), "ready_go.gif");
                gifImageView.setImageDrawable(gifFromAssets);
                gifFromAssets.addAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationCompleted(int loopNumber) {
                        Toast.makeText(getApplicationContext(), "动画播放完成", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            gifFromAssets.reset();
            gifFromAssets.start();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(gifFromAssets != null){
            gifFromAssets.recycle();
        }
    }

    public void go(View view) {
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }

    public void toShake(View view) {
        Intent intent = new Intent(this,ShakeActivity.class);
        startActivity(intent);
    }
}
