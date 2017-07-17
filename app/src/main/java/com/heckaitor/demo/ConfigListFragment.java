package com.heckaitor.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ConfigListFragment extends Fragment {
    
    private static final String KEY_CATEGORY = "CATEGORY";
    
    private Config.Category mCategory;
    
    public static ConfigListFragment newInstance(Config.Category category) {
        Bundle args = new Bundle();
        args.putString(KEY_CATEGORY, category.name());
        ConfigListFragment fragment = new ConfigListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategory = Config.Category.valueOf(getArguments().getString(KEY_CATEGORY));
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_config_list, container, false);
        view.setAdapter(new DemoAdapter(Config.getList(mCategory)));
        return view;
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
        public DemoAdapter.DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_config_layout, parent, false);
            DemoAdapter.DemoViewHolder holder = new DemoAdapter.DemoViewHolder(view);
            return holder;
        }
        
        @Override
        public void onBindViewHolder(DemoAdapter.DemoViewHolder holder, int position) {
            final Config.DemoDesc info = getItem(position);
            holder.titleView.setText(info != null ? info.title : "");
            holder.descView.setText(info != null ? info.desc : "");
            holder.itemView.setTag(info);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Config.DemoDesc info = (Config.DemoDesc) v.getTag();
                    if (info != null && info.target != null) {
                        Intent intent = new Intent(getActivity(), info.target);
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
