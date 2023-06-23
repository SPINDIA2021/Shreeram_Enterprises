package com.satmatgroup.shreeram.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by admin on 5/3/2016.
 */
@SuppressLint("AppCompatCustomView")
public class CustomTextviewHeading extends TextView {

    public CustomTextviewHeading(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextviewHeading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public CustomTextviewHeading(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Impacted.ttf");
        super.setTypeface(tf);
    }
}

