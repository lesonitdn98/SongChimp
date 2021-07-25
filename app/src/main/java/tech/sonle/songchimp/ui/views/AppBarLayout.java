package tech.sonle.songchimp.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import tech.sonle.songchimp.R;

public class AppBarLayout extends LinearLayout {

    private TextView mTvTitle;
    private ImageView mBtnNavLeft, mBtnNavRight;

    public void setTitle(String title) {
        mTvTitle.setVisibility(VISIBLE);
        mTvTitle.setText(title);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setIconNavLeft(int iconNavLeft, OnClickListener onClickListener) {
        mBtnNavLeft.setVisibility(VISIBLE);
        mBtnNavLeft.setImageDrawable(getResources().getDrawable(iconNavLeft));
        mBtnNavLeft.setOnClickListener(onClickListener);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setIconNavRight(int iconNavRight, OnClickListener onClickListener) {
        mBtnNavRight.setVisibility(VISIBLE);
        mBtnNavRight.setImageDrawable(getResources().getDrawable(iconNavRight));
        mBtnNavRight.setOnClickListener(onClickListener);
    }

    public AppBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.app_bar, this, true);

        mTvTitle = findViewById(R.id.tvTitle);
        mBtnNavLeft = findViewById(R.id.btnNavLeft);
        mBtnNavRight = findViewById(R.id.btnNavRight);

        @SuppressLint("Recycle") TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AppBarLayout);
        try {
            String title = typedArray.getString(R.styleable.AppBarLayout_appBarTitle);
            mTvTitle.setVisibility(VISIBLE);
            mTvTitle.setText(title);
        } finally {
            typedArray.recycle();
        }
    }
}
