package eu.inloop.screeninfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import java.util.Locale;

/**
 * Created by miroslavmichalec on 14/09/15.
 */
public class WidthBox
        extends View {

    private static final String AXIS_TEXT_FORMAT = "%dpx (%ddp)  ~ %.1f\" (%.1fcm)";

    private static final int DIMEN_LINE_MARGIN = 25;
    private static final int DIMEN_LINE_DESC_MARGIN = DIMEN_LINE_MARGIN + 5;

    private int mWidth;
    private int mWidthDp;
    private int mHeight;
    private int mHeightDp;

    private float mWidthInches;
    private float mHeightInches;

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

        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.primary_dark));
        mPaint.setStrokeWidth(1 * mDensity);

        mPaint.setTextSize(16 * mDensity);

        canvas.drawLine(
                mWidth - (DIMEN_LINE_MARGIN * mDensity),
                0,
                mWidth - (DIMEN_LINE_MARGIN * mDensity),
                mHeight,
                mPaint);
        canvas.drawLine(
                0,
                mHeight - (DIMEN_LINE_MARGIN * mDensity),
                mWidth,
                mHeight - (DIMEN_LINE_MARGIN * mDensity), mPaint);

        canvas.save();
        canvas.rotate(270, mWidth - (DIMEN_LINE_DESC_MARGIN * mDensity), mHeight / 2);
        canvas.drawText(
                String.format(Locale.getDefault(), AXIS_TEXT_FORMAT, mHeight, mHeightDp, mHeightInches, mHeightInches * MainActivity.CM_PER_INCH),
                mWidth - (DIMEN_LINE_DESC_MARGIN * mDensity),
                mHeight / 2,
                mPaint);
        canvas.restore();

        canvas.drawText(
                String.format(Locale.getDefault(), AXIS_TEXT_FORMAT, mWidth, mWidthDp, mWidthInches, mWidthInches * MainActivity.CM_PER_INCH),
                mWidth / 2,
                mHeight - (DIMEN_LINE_DESC_MARGIN * mDensity),
                mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        mWidthDp = (int) (w / mDensity);
        mHeightDp = (int) (h / mDensity);

        if (!isInEditMode()) {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
            mWidthInches = mWidth / dm.xdpi;
            mHeightInches = mHeight / dm.ydpi;
        }
    }
}
