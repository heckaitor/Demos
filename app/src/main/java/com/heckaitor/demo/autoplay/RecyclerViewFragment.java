package com.heckaitor.demo.autoplay;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heckaitor.autoplay.AutoPlayManager;
import com.heckaitor.demo.R;

/**
 * Created by kaige1 on 2018/4/27.
 */
public class RecyclerViewFragment extends Fragment {

    public static RecyclerViewFragment create() {
        return new RecyclerViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_view, container, false);
        view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        view.setAdapter(mAdapter);
        view.addItemDecoration(new DividerItemDecoration(container.getContext(), DividerItemDecoration.VERTICAL));
        AutoPlayManager.bind(view).detect();
        return view;
    }

    private RecyclerView.Adapter<SampleViewHolder> mAdapter = new RecyclerView.Adapter<SampleViewHolder>() {
        @Override
        public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LightItemView itemView = new LightItemView(parent.getContext());
            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            itemView.setRatio(1.78f);
            return new SampleViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(SampleViewHolder holder, int position) {
            final LightItemView itemView = (LightItemView) holder.itemView;
            itemView.setText(String.valueOf(position));
            itemView.setTag("item " + position);
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    };

    private class SampleViewHolder extends RecyclerView.ViewHolder {
        public SampleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
