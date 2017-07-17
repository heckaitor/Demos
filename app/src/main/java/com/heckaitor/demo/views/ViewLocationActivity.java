package com.heckaitor.demo.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.heckaitor.demo.R;

public class ViewLocationActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location_layout);
        
        Log.i("demo", "locate when onCreate");
        locate();
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.i("demo", "locate when hasFocus");
            locate();
        }
    }
    
    private void locate() {
        logViewLocation(findViewById(R.id.tv_view1));
        logViewLocation(findViewById(R.id.tv_gone_view));
    }
    
    private void logViewLocation(View view) {
        if (view == null) {
            return;
        }
        
        int[] pos = new int[2];
        view.getLocationOnScreen(pos);
        Log.d("demo", "pos = " + pos[0] + ", " + pos[1]);
    }
    
}
