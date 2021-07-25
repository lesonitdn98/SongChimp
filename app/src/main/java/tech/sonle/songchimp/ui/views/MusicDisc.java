package tech.sonle.songchimp.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.sonle.songchimp.R;

public class MusicDisc extends LinearLayout {

    public void setSongThumbnail(Bitmap songThumbnail) {
        Glide.with(this).load(songThumbnail).into((CircleImageView) findViewById(R.id.songThumbnail));
    }

    public MusicDisc(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.disc_music, this, true);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        FrameLayout layoutDiscMusic = findViewById(R.id.layoutDiscMusic);
        layoutDiscMusic.setLayoutParams(new LayoutParams(widthMeasureSpec, heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
