package com.heckaitor.demo.views;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.heckaitor.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UiVisibilityActivity extends AppCompatActivity {
    
    @BindView(R.id.tv_target)
    TextView mTargetView;
    
    private Point mScreenSize;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_visibility_demo);
        ButterKnife.bind(this);
        transparentTitleAndNavigation();
        
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                logInfo(visibility);
            }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ui_visibilit_options, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.flag_low_profile) {
            setUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        } else if (id == R.id.flag_hide_navigation) {
            setUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else if (id == R.id.flag_fullscreen) {
            setUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else if (id == R.id.flag_layout_stable) {
            setUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else if (id == R.id.flag_layout_hide_navigation) {
            setUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        } else if (id == R.id.flag_layout_fullscreen) {
            setUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (id == R.id.flag_immersive) {
            setUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
        } else if (id == R.id.flag_immersive_sticky) {
            setUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    
        final CharSequence title = item.getTitle();
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
    
    @OnClick(R.id.btn_fullscreen)
    public void fullScreen() {
        final int options = View.SYSTEM_UI_FLAG_FULLSCREEN;
        setUiVisibility(options);
        getSupportActionBar().hide();
    }
    
    @OnClick(R.id.btn_transparent_title_and_navigation)
    public void transparentTitleAndNavigation() {
        final int options = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        setUiVisibility(options);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
        
        getSupportActionBar().hide();
    }
    
    @OnClick(R.id.btn_immersive)
    public void immersiveMode() {
        if (Build.VERSION.SDK_INT >= 19) {
            final int options = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            setUiVisibility(options);
        }
    
        getSupportActionBar().hide();
    }
    
    private void setUiVisibility(int options) {
        mTargetView.setSystemUiVisibility(options);
    }
    
    private void logInfo(int visibility) {
        if (mScreenSize == null) {
            mScreenSize = new Point();
        }
    
        getWindowManager().getDefaultDisplay().getSize(mScreenSize);
        StringBuilder builder = new StringBuilder();
        builder.append("visibility = ").append(visibility)
                .append("\nScreen = [")
                .append(mScreenSize.x)
                .append(", ")
                .append(mScreenSize.y).append("]\n");
        
        mTargetView.setText(builder);
    }
    
}
