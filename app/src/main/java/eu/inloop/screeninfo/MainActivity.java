package eu.inloop.screeninfo;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity
        extends AppCompatActivity {

    private static final int UI_ANIMATION_DELAY = 300;
    static final float CM_PER_INCH = 2.54f;

    private View mContentView;
    private boolean mVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mVisible = true;

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        mContentView = findViewById(R.id.fullscreen_content);

        mContentView.setOnClickListener(view -> toggle());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        CheckBox toggleAB = findViewById(R.id.toggleAB);
        toggleAB.setChecked(false);
        toggleAB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ActionBar actionBar1 = getSupportActionBar();
            if (actionBar1 != null) {
                if (isChecked) {
                    actionBar1.show();
                } else {
                    actionBar1.hide();
                }
            }
        });

        float density = getResources().getDisplayMetrics().density;
        TextView densityNumber = findViewById(R.id.densityNumber);
        densityNumber.setText(getString(R.string.density_number, Float.toString(density)));

        setRealDeviceSizeInPixels();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedHide();
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        mVisible = false;
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            int flags = mContentView.getSystemUiVisibility();
            flags = setFlag(flags, View.SYSTEM_UI_FLAG_FULLSCREEN);
            flags = setFlag(flags, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            mContentView.setSystemUiVisibility(flags);
        }
    };

    @SuppressLint("InlinedApi")
    private void show() {
        int flags = mContentView.getSystemUiVisibility();
        flags = setFlag(flags, View.SYSTEM_UI_FLAG_FULLSCREEN);
        flags = resetFlag(flags, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mContentView.setSystemUiVisibility(flags);

        mVisible = true;

        mHideHandler.removeCallbacks(mHidePart2Runnable);
    }

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = this::hide;

    private void delayedHide() {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 200);
    }

    private int setFlag(int flags, int flag) {
        if ((flags & flag) == 0) {
            flags |= flag;
        }
        return flags;
    }

    private int resetFlag(int flags, int flag) {
        if ((flags & flag) == flag) {
            flags &= ~flag;
        }
        return flags;
    }

    // Copied from: http://stackoverflow.com/questions/2193457/is-there-a-way-to-determine-android-physical-screen-height-in-cm-or-inches
    private void setRealDeviceSizeInPixels() {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        // since SDK_INT = 1;
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;

        // includes window decorations (statusbar bar/menu bar)
        try {
            Point realSize = new Point();
            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            widthPixels = realSize.x;
            heightPixels = realSize.y;
        } catch (Exception ignored) {
        }

        double diagonalInches = Math.sqrt(Math.pow(widthPixels / displayMetrics.xdpi, 2) + Math.pow(heightPixels / displayMetrics.ydpi, 2));
        TextView diameter = findViewById(R.id.diagonal);
        diameter.setText(String.format(Locale.getDefault(), "~ %.1f\"  (%.1fcm)", diagonalInches, diagonalInches * CM_PER_INCH));
    }
}
