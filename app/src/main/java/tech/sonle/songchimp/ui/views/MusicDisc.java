package tech.sonle.songchimp.ui.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.sonle.songchimp.R;

public class MusicDisc extends LinearLayout {

    private ObjectAnimator mRotateAnimated;

    public void setSongThumbnail(Bitmap songThumbnail) {
        Glide.with(this).load(songThumbnail).placeholder(R.drawable.music_default).into((CircleImageView) findViewById(R.id.songThumbnail));
    }

    public MusicDisc(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.disc_music, this, true);
        addRotateAnimation();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        FrameLayout layoutDiscMusic = findViewById(R.id.layoutDiscMusic);
        layoutDiscMusic.setLayoutParams(new LayoutParams(widthMeasureSpec, heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void addRotateAnimation() {
        mRotateAnimated = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);
        mRotateAnimated.setInterpolator(new LinearInterpolator());
        mRotateAnimated.setRepeatCount(ValueAnimator.INFINITE);
        mRotateAnimated.setDuration(2400);
        mRotateAnimated.start();
    }

    public void startRotateAnimation() {
        mRotateAnimated.resume();
    }

    public void stopRotateAnimation() {
        mRotateAnimated.pause();
    }
}
