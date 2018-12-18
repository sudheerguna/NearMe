package com.product.nearme.cview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by sunanu on 8/22/2016.
 */
public class TypefaceWebfontTextview extends TextView {
    public TypefaceWebfontTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TypefaceWebfontTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TypefaceWebfontTextview(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/fontawesome-webfont.ttf");
        setTypeface(tf);
    }
}
