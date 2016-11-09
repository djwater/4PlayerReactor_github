package com.yedona.a4playerreactor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/11/9.
 */

public class BallView extends View {

    private Paint paint;
    private int radius;
    private int color;

    public BallView(Context context) {
        this(context, null);
    }

    public BallView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();

    }

    public void setColor(int color) {
        this.color = color;
    }
    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        canvas.drawCircle(radius, radius, radius, paint);
    }
}
