package com.heckaitor.demo.autoplay;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.heckaitor.autoplay.AutoPlayManager;
import com.heckaitor.demo.R;
import com.heckaitor.demo.util.NullFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AutoPlayActivity extends AppCompatActivity {

    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    private FragmentStatePagerAdapter mPagerAdapter
            = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return RecyclerViewFragment.create();
                case 1:
                    return ListViewFragment.create();
                case 2:
                default:
                    return NullFragment.create(Color.BLUE);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_play);
        ButterKnife.bind(this);
        AutoPlayManager.init(this);

        mViewPager.setAdapter(mPagerAdapter);
    }
}
