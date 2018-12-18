package com.product.nearme.cview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by guna on 23-09-2016.
 */
public class TypefaceBoldTextview extends TextView {
    public TypefaceBoldTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TypefaceBoldTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TypefaceBoldTextview(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/SF-Pro-Display-Semibold.otf");
        setTypeface(tf);
    }
}
