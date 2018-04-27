package com.heckaitor.demo.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.heckaitor.demo.Config;
import com.heckaitor.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {
    
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;
    
    @BindView(R.id.vp_content)
    ViewPager mPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        
        mNavigationView.setOnNavigationItemSelectedListener(this);
        mPager.setAdapter(mStatePagerAdapter);
        mPager.addOnPageChangeListener(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
    
        return super.onOptionsItemSelected(item);
    }
    
    private FragmentStatePagerAdapter mStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return ConfigListFragment.newInstance(getCategory(position));
        }
        
        Config.Category getCategory(int position) {
            return Config.Category.values()[position];
        }
    
        @Override
        public int getCount() {
            return Config.Category.values().length;
        }
    };
    
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
    
    @Override
    public void onPageScrollStateChanged(int state) { }
    
    @Override
    public void onPageSelected(int position) {
        final int id = index2Item(position);
        if (id > 0) {
            mNavigationView.setSelectedItemId(index2Item(position));
        }
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int index = item2Index(item.getItemId());
        if (index >= 0) {
            mPager.setCurrentItem(index);
            return true;
        }
        return false;
    }
    
    private int item2Index(int itemId) {
        switch (itemId) {
            case R.id.nav_content: return 0;
            case R.id.nav_views: return 1;
            case R.id.nav_animates: return 2;
            case R.id.nav_tools: return 3;
            default: return -1;
        }
    }
    
    private int index2Item(int index) {
        switch (index) {
            case 0: return R.id.nav_content;
            case 1: return R.id.nav_views;
            case 2: return R.id.nav_animates;
            case 3: return R.id.nav_tools;
            default: return -1;
        }
    }
}
