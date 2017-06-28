package com.heckaitor.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
    
    private static final String TAG = MainActivity.class.getSimpleName();
    
    private RecyclerView mDemoView;
    private DemoAdapter mDemoAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Snack Barrrrrrrrr!", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
        
        mDemoView = (RecyclerView) findViewById(R.id.rv_content);
        mDemoView.setLayoutManager(new LinearLayoutManager(this));
        mDemoAdapter = new DemoAdapter(Config.getList(Config.Category.VIEW));
        mDemoView.setAdapter(mDemoAdapter);
        //mDemoView.addItemDecoration(new DividerItemDecoration());
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
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
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		final int id = item.getItemId();
		if (id == R.id.nav_gallery) {
            mDemoAdapter.setList(Config.getList(Config.Category.VIEW));
        } else if (id == R.id.nav_slideshow) {
            mDemoAdapter.setList(Config.getList(Config.Category.CONTENT));
        } else if (id == R.id.nav_manage) {
            mDemoAdapter.setList(Config.getList(Config.Category.TOOL));
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	private class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoViewHolder> {
        
        private List<Config.DemoDesc> mList = new ArrayList<>();
        
        public DemoAdapter(List<Config.DemoDesc> list) {
            this.mList = list;
        }
        
        public void setList(List list) {
            this.mList = list;
            notifyDataSetChanged();
        }
        
        @Override
        public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo_layout, parent, false);
            DemoViewHolder holder = new DemoViewHolder(view);
            return holder;
        }
        
        @Override
        public void onBindViewHolder(DemoViewHolder holder, int position) {
            final Config.DemoDesc info = getItem(position);
            holder.titleView.setText(info != null ? info.title : "");
            holder.descView.setText(info != null ? info.desc : "");
            holder.itemView.setTag(info);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Config.DemoDesc info = (Config.DemoDesc) v.getTag();
                    if (info != null && info.target != null) {
                        Intent intent = new Intent(MainActivity.this, info.target);
                        startActivity(intent);
                    }
                }
            });
        }
        
        public Config.DemoDesc getItem(int position) {
            if (mList == null || mList.isEmpty()) {
                return null;
            }
    
            if (position >= 0 && position < mList.size()) {
                return mList.get(position);
            }
            
            return null;
        }
        
        @Override
        public int getItemCount() {
            final int count = mList != null ? mList.size() : 0;
            return count;
        }
        
        public class DemoViewHolder extends RecyclerView.ViewHolder {
            
            View itemView;
            TextView titleView;
            TextView descView;
    
            public DemoViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                this.titleView = (TextView) itemView.findViewById(R.id.tv_title);
                this.descView = (TextView) itemView.findViewById(R.id.tv_desc);
            }
        }
    }
}
