package com.example.xh.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.xh.R;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button start_FrameAnimation, start_tween, start_property;
    private ImageView imageView;
    private Boolean isIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
        start_FrameAnimation = (Button) findViewById(R.id.start_frame);
        imageView = (ImageView) findViewById(R.id.imageView);
        start_tween = (Button) findViewById(R.id.start_tween);
        start_property = (Button) findViewById(R.id.start_property);
        start_FrameAnimation.setOnClickListener(this);
        start_tween.setOnClickListener(this);
        start_property.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        imageView.setAlpha(1f);
        switch (v.getId()) {
            case R.id.start_frame:
/*                imageView.setBackgroundResource(R.drawable.frame_run_animation);
                AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
                animationDrawable.start();*/

                imageView.setImageDrawable(null);
                addFrame();
                break;
            case R.id.start_tween:
                imageView.setBackgroundDrawable(null);

                imageView.setImageDrawable(this.getResources().getDrawable(R.drawable.red_circle));
                int id;
                if (isIn) {
                    id = R.anim.anim_in_left_top;
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    id = R.anim.anim_out_left_top;
                }
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_in_left_top);
                isIn = !isIn;
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView.startAnimation(animation);
                break;
            case R.id.start_property:
                imageView.setImageResource(R.mipmap.ic_launcher);
                startPropertyAnimation4();
                break;
        }
    }

    private void startPropertyAnimation() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.property_animator);
        set.setTarget(imageView);
        set.start();

    }

    private void startPropertyAnimation2() {
        ObjectAnimator.ofFloat(imageView, "rotation", 0, 180, 90, 180)
                .setDuration(2000).start();
    }

    private void startPropertyAnimation3() {
        PropertyValuesHolder translationX = PropertyValuesHolder
                .ofFloat("translationX", -200, -100, 100, 200, 300);
        PropertyValuesHolder scaleX = PropertyValuesHolder
                .ofFloat("scaleX", 1.0f, 2.0f);
        PropertyValuesHolder rotate = PropertyValuesHolder
                .ofFloat("rotation", 0f, 360f);
        PropertyValuesHolder rotationX = PropertyValuesHolder
                .ofFloat("rotationX", 0f, 180f);
        ObjectAnimator together = ObjectAnimator
                .ofPropertyValuesHolder(imageView, translationX, rotate, scaleX, rotationX);
        together.setDuration(3000);
        together.start();
    }

    //或者使用AnimatorSet,此方法使用的是按顺序播放。
    private void startPropertyAnimation4() {
        //属性动画，属性名后面的值是可别参数，可传任意个数，如-200, -100, 100, 200, 300，属性值会按给出的值发生变化。
        ObjectAnimator translationX = ObjectAnimator
                .ofFloat(imageView, "translationX", -200, -100, 100, 200, 300);
        ObjectAnimator scaleX = ObjectAnimator
                .ofFloat(imageView, "scaleX", 1.0f, 2.0f)
                .setDuration(1000);
        ObjectAnimator rotation = ObjectAnimator
                .ofFloat(imageView, "rotation", 0f, 360f)
                .setDuration(1000);
        ObjectAnimator rotationX = ObjectAnimator
                .ofFloat(imageView, "rotationX", 0f, 180f)
                .setDuration(1000);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(translationX, scaleX, rotation, rotationX);
        set.setDuration(4000);
        set.start();
    }

    private void addFrame() {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        int[] mipmaps = new int[]{R.mipmap.run1, R.mipmap.run2, R.mipmap.run3,
                R.mipmap.run4, R.mipmap.run5, R.mipmap.run6, R.mipmap.run7, R.mipmap.run8,};
        for (int i = 1; i <= 8; i++) {
            int id = mipmaps[i - 1];
            //int id = getResources().getIdentifier("run" + i, "mipmap", getPackageName());
            Drawable drawable = getResources().getDrawable(id);
            animationDrawable.addFrame(drawable, 200);
        }
        animationDrawable.setOneShot(false);
        imageView.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();
    }
}
