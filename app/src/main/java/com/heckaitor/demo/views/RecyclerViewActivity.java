package com.heckaitor.demo.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heckaitor.demo.R;
import com.heckaitor.demo.utils.StringUtils;
import com.heckaitor.demo.utils.log.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends AppCompatActivity {
    
    @BindView(R.id.rv_content)
    RecyclerView mView;
    private ContentAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ContentAdapter();

        mView.setLayoutManager(mLayoutManager);
        mView.setAdapter(mAdapter);
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                final int firstVisibleIndex = mLayoutManager.findFirstVisibleItemPosition();
                final int visibleCount = mLayoutManager.findLastVisibleItemPosition() - firstVisibleIndex + 1;
                final int childCount = recyclerView.getChildCount();
                final int firstIndex = recyclerView.indexOfChild(recyclerView.getChildAt(0));
                Logger.v(recyclerView, "Manager -> " + firstVisibleIndex + "/" + visibleCount + ", View -> " + firstIndex + "/" + childCount);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (R.id.action_settings == id) {
            final int position = mLayoutManager.findFirstVisibleItemPosition() + 1;
            String content = mAdapter.data.get(position);
            mAdapter.data.set(position, content + " new");
            mAdapter.notifyItemChanged(position);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

        private List<String> data;

        public ContentAdapter() {
            final int SIZE = 30;
            data = new ArrayList<>(SIZE);
            for (int i = 0; i < SIZE; i++) {
                data.add("item " + i);
            }
        }

        @Override
        public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Logger.v(this, "onCreateViewHolder");
            final View view = getLayoutInflater().inflate(R.layout.item_imge_text, parent, false);
            view.addOnAttachStateChangeListener(mAttachStateListener);
            ContentViewHolder holder = new ContentViewHolder(view);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return holder;
        }
    
        @Override
        public void onBindViewHolder(ContentViewHolder holder, int position) {
            Logger.d(this, "onBindViewHolder", String.valueOf(position));
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
            holder.textView.setText(data.get(position));
        }
    
        @Override
        public int getItemCount() {
            return data.size();
        }
    
        class ContentViewHolder extends RecyclerView.ViewHolder {
            
            ImageView imageView;
            TextView textView;
    
            public ContentViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.iv_image);
                textView = (TextView) itemView.findViewById(R.id.tv_text);
            }
        }
        
    }
    
    private View.OnAttachStateChangeListener mAttachStateListener = new View.OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(View v) {
            Logger.i(RecyclerViewActivity.this, "onViewAttachedToWindow", StringUtils.commonToString(v));
        }
    
        @Override
        public void onViewDetachedFromWindow(View v) {
            Logger.w(RecyclerViewActivity.this, "onViewDetachedFromWindow", StringUtils.commonToString(v));
        }
    };
}
