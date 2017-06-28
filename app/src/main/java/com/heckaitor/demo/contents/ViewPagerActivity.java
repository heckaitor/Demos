package com.heckaitor.demo.contents;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.heckaitor.demo.R;

public class ViewPagerActivity extends AppCompatActivity {
    
    private static final String TAG = "viewpager";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private PagerAdapter mSectionAdapter;
    
    private ViewPager mViewPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionAdapter);
        
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_pager, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_pager_adapter) {
            mSectionAdapter = new SectionPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mSectionAdapter);
            return true;
        } else if (id == R.id.action_state_pager_adapter) {
            mSectionAdapter = new SectionStatePagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mSectionAdapter);
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        
        private TextView mTextView;
        private String mTag;
        
        public PlaceholderFragment() { }
    
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
    
        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Log.v(TAG, this + ": onAttach");
        }
    
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mTag = String.valueOf(getArguments().getInt(ARG_SECTION_NUMBER));
            Log.v(TAG, this + ": onCreate: " + savedInstanceState);
        }
    
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Log.v(TAG, this + ": onCreateView: " + savedInstanceState);
            View rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);
            mTextView = (TextView) rootView.findViewById(R.id.section_label);
            mTextView.setText(getString(R.string.section_format, mTag));
            return rootView;
        }
    
        @Override
        public void onResume() {
            super.onResume();
            Log.v(TAG, this + ": onResume");
        }
    
        @Override
        public void onPause() {
            super.onPause();
            Log.v(TAG, this + ": onPause");
        }
    
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.v(TAG, this + ": onDestroyView");
        }
    
        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.v(TAG, this + ": onDestroy");
        }
    
        @Override
        public void onDetach() {
            super.onDetach();
            Log.v(TAG, this + ": onDetach");
        }
    
        View getContentView() {
            return mTextView;
        }
    
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + ": " + mTag;
        }
    }
    
    public class SectionPagerAdapter extends FragmentPagerAdapter {
        
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        
        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }
        
        @Override
        public int getCount() {
            return 3;
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
    
    public class SectionStatePagerAdapter extends FragmentStatePagerAdapter {
    
        public SectionStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }
    
        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }
    
        @Override
        public int getCount() {
            return 3;
        }
    }
    
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        
        }
    
        @Override
        public void onPageSelected(final int position) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    logItemInfo(position);
                }
            }, 1500);
        }
    
        @Override
        public void onPageScrollStateChanged(int state) {
        
        }
    };
    
    private void logItemInfo(int position) {
        PlaceholderFragment fragment = null;
        if (mSectionAdapter instanceof FragmentPagerAdapter) {
            fragment = (PlaceholderFragment) ((FragmentPagerAdapter) mSectionAdapter).getItem(position);
        } else if(mSectionAdapter instanceof FragmentStatePagerAdapter) {
            fragment = (PlaceholderFragment) ((FragmentStatePagerAdapter) mSectionAdapter).getItem(position);
        }
        Log.i(TAG, "fragment: " + fragment);
        
        final View view = fragment.getContentView();
        Log.d(TAG, "view: " + view);
    }
    
    private Handler mHandler = new Handler(Looper.getMainLooper());
}
