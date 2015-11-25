package eu.inloop.screeninfo;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private boolean mVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mVisible = true;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        mContentView = findViewById(R.id.fullscreen_content);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            mContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle();
                }
            });
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        CheckBox toggleAB = (CheckBox) findViewById(R.id.toggleAB);
        toggleAB.setChecked(false);
        toggleAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    if (isChecked) {
                        actionBar.show();
                    } else {
                        actionBar.hide();
                    }
                }
            }
        });

        float density = getResources().getDisplayMetrics().density;
        TextView densityNumber = (TextView) findViewById(R.id.densityNumber);
        densityNumber.setText(getString(R.string.density_number, Float.toString(density)));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            delayedHide(200);
        }
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
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
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

}
