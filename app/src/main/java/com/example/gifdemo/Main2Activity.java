package com.example.gifdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import java.io.IOException;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Main2Activity extends AppCompatActivity {

    private GifDrawable gifFromAssets;
    private GifImageView gifImageView_l, gifImageView, gifImageView_r;
    private GifDrawable gifFromAssetsLeft;
    private GifDrawable gifFromAssetsRight;
    private ImageView doll;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        gifImageView_l = findViewById(R.id.gif_view_left);
        gifImageView = findViewById(R.id.gif_view);
        gifImageView_r = findViewById(R.id.gif_view_right);
        doll = findViewById(R.id.iv_doll);
    }


    public void startAnimation(View view) {


        if (gifFromAssetsLeft != null && gifFromAssetsLeft.isPlaying()) {
            return;
        }

        if (gifFromAssetsLeft == null) {
            try {

                gifFromAssetsLeft = new GifDrawable(getAssets(), "anime_firework_left.gif");
                gifFromAssets = new GifDrawable(getAssets(), "anime_gamewin.gif");
                gifFromAssetsRight = new GifDrawable(getAssets(), "anime_firework_right.gif");

                gifImageView_l.setImageDrawable(gifFromAssetsLeft);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gifImageView_r.setImageDrawable(gifFromAssetsRight);
                        gifImageView.setImageDrawable(gifFromAssets);
                        gifFromAssets.setLoopCount(1);
                    }
                }, 1000);


                gifFromAssets.addAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationCompleted(int loopNumber) {

                        ObjectAnimator animatorX = ObjectAnimator.ofFloat(doll,View.SCALE_X ,0,1.2f,1);
                        ObjectAnimator animatorY = ObjectAnimator.ofFloat(doll,View.SCALE_Y ,0,1.2f,1);

                        AnimatorSet set = new AnimatorSet();

                        set.playTogether(animatorX,animatorY);
                        set.setDuration(2000);

                        set.setInterpolator(new BounceInterpolator());
                        set.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                doll.setVisibility(View.VISIBLE);
                            }
                        });
                        set.start();

                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gifFromAssetsLeft != null) {
            gifFromAssetsLeft.recycle();
        }
        if (gifFromAssetsRight != null) {
            gifFromAssetsRight.recycle();
        }
        if (gifFromAssets != null) {
            gifFromAssets.recycle();
        }

    }

    public void stop(View view) {

        gifImageView_l.setImageDrawable(null);
        gifImageView.setImageDrawable(null);
        gifImageView_r.setImageDrawable(null);

        doll.setVisibility(View.GONE);

        clearGif(gifFromAssetsRight);
        clearGif(gifFromAssetsLeft);
        clearGif(gifFromAssets);

        gifFromAssets = null;
        gifFromAssetsLeft = null;
        gifFromAssetsRight = null;

    }


    public void clearGif(GifDrawable gifDrawable) {
        if (gifDrawable != null) {
            gifDrawable.stop();
            gifDrawable.recycle();

        }

    }


}
