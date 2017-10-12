package com.game.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by dabba on 12/10/17.
 */

public class GameAreaImageView extends AppCompatImageView {

    public GameAreaImageView(Context context) {
        super(context);
    }

    public GameAreaImageView(Context context, AttributeSet attrst) {
        super(context, attrst);
    }

    public GameAreaImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(50, 50, 10, paint);
        super.onDraw(canvas);
        this.postInvalidate();
    }
}