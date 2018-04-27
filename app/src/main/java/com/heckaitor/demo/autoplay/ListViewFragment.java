package com.heckaitor.demo.autoplay;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.heckaitor.autoplay.AutoPlayManager;
import com.heckaitor.demo.R;

/**
 * Created by kaige1 on 2018/4/27.
 */
public class ListViewFragment extends Fragment {

    public static ListViewFragment create() {
        return new ListViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView view = (ListView) inflater.inflate(R.layout.fragment_list_view, container, false);
        view.setAdapter(mAdapter);
        view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        AutoPlayManager.bind(view).detect();
        return view;
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return String.valueOf(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                holder.itemView = new LightItemView(parent.getContext());
                holder.itemView.setRatio(1.78f);
                convertView = holder.itemView;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.itemView.setText((String) getItem(position));
            holder.itemView.setBackgroundColor(Color.LTGRAY);
            return convertView;
        }
    };

    private class ViewHolder {
        LightItemView itemView;
    }
}

