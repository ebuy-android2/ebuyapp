package com.example.admin.ebuy.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class EBCustomFont extends TextView {


    public EBCustomFont(Context context) {
        super(context);
        init();
    }

    public EBCustomFont(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EBCustomFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public EBCustomFont(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/OpenSans-Light.ttf");
        setTypeface(tf, 0);

    }
}
