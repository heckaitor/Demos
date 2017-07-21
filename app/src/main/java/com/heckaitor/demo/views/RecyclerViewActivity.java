package com.heckaitor.demo.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heckaitor.demo.R;
import com.heckaitor.demo.utils.StringUtils;
import com.heckaitor.demo.utils.log.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends AppCompatActivity {
    
    @BindView(R.id.rv_content) RecyclerView mView;
    private ContentAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);
        
        mView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ContentAdapter();
        mView.setAdapter(mAdapter);
    }
    
    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    
    
        @Override
        public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Logger.v(this, "onCreateViewHolder");
            final View view = getLayoutInflater().inflate(R.layout.item_imge_text, parent, false);
            view.addOnAttachStateChangeListener(mAttachStateListener);
            return new ContentViewHolder(view);
        }
    
        @Override
        public void onBindViewHolder(ContentViewHolder holder, int position) {
            Logger.d(this, "onBindViewHolder", String.valueOf(position));
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
            holder.textView.setText("item " + position);
        }
    
        @Override
        public int getItemCount() {
            return 30;
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
