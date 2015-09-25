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

    private static final int SQUARE_DP = 100;  //dp
    private static final int SQUARE_PX = 100;  //px

    private static final int TOP_MARGIN_DP = 10;

    private static final int FONT_SIZE_SMALL = 12;
    private static final int FONT_SIZE_BIG = 16;

    private static final int DP_SQUARE_LEFT_MARGIN_DP = 10;
    private static final int PX_SQUARE_LEFT_MARGIN_DP = 150;

    private static final int DIMEN_LINE_MARGIN = 50;
    private static final int DIMEN_LINE_DESC_MARGIN = 55;

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
        canvas.drawRect(
                DP_SQUARE_LEFT_MARGIN_DP * mDensity,
                TOP_MARGIN_DP * mDensity,
                (DP_SQUARE_LEFT_MARGIN_DP + SQUARE_DP) * mDensity,
                (TOP_MARGIN_DP + SQUARE_DP) * mDensity,
                mPaint);

        canvas.drawRect(
                PX_SQUARE_LEFT_MARGIN_DP * mDensity,
                TOP_MARGIN_DP * mDensity,
                (PX_SQUARE_LEFT_MARGIN_DP * mDensity) + SQUARE_PX,
                (TOP_MARGIN_DP * mDensity) + SQUARE_PX,
                mPaint);

        mPaint.setTextSize(FONT_SIZE_SMALL * mDensity);
        mPaint.setColor(Color.WHITE);
        canvas.drawText(
                Integer.toString(SQUARE_DP) + " dp x " + Integer.toString(SQUARE_DP) + " dp",
                (DP_SQUARE_LEFT_MARGIN_DP + SQUARE_DP / 2) * mDensity,
                (TOP_MARGIN_DP + SQUARE_DP / 2) * mDensity,
                mPaint);

        mPaint.setColor(Color.BLACK);
        canvas.drawText(
                Integer.toString(SQUARE_PX) + " px x " + Integer.toString(SQUARE_PX) + " px",
                (PX_SQUARE_LEFT_MARGIN_DP * mDensity) + SQUARE_PX / 2,
                (30 * mDensity) + SQUARE_PX,
                mPaint);

        mPaint.setTextSize(FONT_SIZE_BIG * mDensity);
        mPaint.setColor(Color.BLUE);
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
                Integer.toString(mHeight) + " px (" + Integer.toString(mHeightDp) + " dp)",
                mWidth - (DIMEN_LINE_DESC_MARGIN * mDensity),
                mHeight / 2,
                mPaint);
        canvas.restore();

        canvas.drawText(
                Integer.toString(mWidth) + " px (" + Integer.toString(mWidthDp) + " dp)",
                mWidth / 2,
                mHeight  - (DIMEN_LINE_DESC_MARGIN * mDensity),
                mPaint);
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
