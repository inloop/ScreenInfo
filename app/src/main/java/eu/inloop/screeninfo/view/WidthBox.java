package eu.inloop.screeninfo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by miroslavmichalec on 14/09/15.
 */
public class WidthBox extends View {

    private int mWidth;
    private int mWidthDp;
    private int mHeight;
    private int mHeightDp;

    private Paint mPaint;

    private float mDensity;

    public WidthBox(Context context) {
        super(context);
        init();
    }

    public WidthBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WidthBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDensity = getContext().getResources().getDisplayMetrics().density;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(2);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.BLUE);
        canvas.drawRect(10 * mDensity, 10 * mDensity, 110 * mDensity, 110 * mDensity, mPaint);

        canvas.drawRect(150 * mDensity, 10 * mDensity, (150 * mDensity) + 100, (10 * mDensity) + 100, mPaint);

        mPaint.setTextSize(12 * mDensity);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("100 dp x 100 dp", 58 * mDensity, 65 * mDensity, mPaint);

        mPaint.setColor(Color.BLACK);
        canvas.drawText("100 px x 100 px", 150 * mDensity + 50, 30 * mDensity + 100, mPaint);

        mPaint.setTextSize(16 * mDensity);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(mWidth - 50 * mDensity, 0, mWidth - 50 * mDensity, mHeight, mPaint);
        canvas.drawLine(0, mHeight - 50 * mDensity, mWidth, mHeight - 50 * mDensity, mPaint);

        canvas.save();
        canvas.rotate(270, mWidth - 55 * mDensity, mHeight / 2);
        canvas.drawText(
                Integer.toString(mHeight) + " px (" + Integer.toString(mHeightDp) + " dp)",
                mWidth - 55 * mDensity, mHeight / 2, mPaint);
        canvas.restore();

        canvas.drawText(
                Integer.toString(mWidth) + " px (" + Integer.toString(mWidthDp) + " dp)",
                mWidth / 2, mHeight  - 55 * mDensity, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        mWidthDp = (int)(w / mDensity);
        mHeightDp = (int)(h / mDensity);
    }

}
