package com.heckaitor.demo.views;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.heckaitor.demo.R;
import com.heckaitor.demo.common.AspectRatioLayout;
import com.heckaitor.demo.common.CommonRecyclerAdapter;
import com.heckaitor.demo.common.DividerItemDecoration;
import com.heckaitor.demo.common.FixScrollRecyclerView;
import com.heckaitor.demo.utils.ViewUtils;
import com.heckaitor.demo.utils.log.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.heckaitor.demo.common.DividerItemDecoration.VERTICAL_LIST;
import static com.heckaitor.demo.utils.StringUtils.commonToString;

public class RecyclerViewActivity extends AppCompatActivity {

    private static final int DOCK_OFFSET = 0;
    
    @BindView(R.id.rv_content)
    FixScrollRecyclerView mView;
    private CommonRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.rg_smooth)
    RadioGroup mSmoothSelectorView;

    @BindView(R.id.et_index)
    EditText mIndexView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        mLayoutManager = new LinearLayoutManager(this);
        //mLayoutManager = new GridLayoutManager(this, 2);
        //mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new CommonRecyclerAdapter(new ContentAdapter());

        // header
        //mAdapter.addHeaderView(createSimpleTextView("header 1"));
        //mAdapter.addHeaderView(createSimpleTextView("header 2"));

        // footer
        //mAdapter.addFooterView(createSimpleTextView("footer 1"));
        //mAdapter.addFooterView(createSimpleTextView("footer 2"));

        mAdapter.setEmptyView(createSimpleTextView("empty view"));

        mView.setLayoutManager(mLayoutManager);
        mView.setAdapter(mAdapter);
        mView.addItemDecoration(new DividerItemDecoration(this, VERTICAL_LIST));

        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Logger.d(RecyclerViewActivity.this, "onScrollStateChanged", ViewUtils.recyclerViewScrollState2String(newState));
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        final LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        Logger.i(RecyclerViewActivity.this, "last = " + manager.findLastVisibleItemPosition(), "count = " + manager.getItemCount());
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //Logger.v(RecyclerViewActivity.this, "onScrolled", dx + ", " + dy);
            }
        });
    }

    @OnClick(R.id.btn_go)
    public void scrollToPosition() {
        final String strIndex = mIndexView.getText().toString();
        try {
            final int index = Integer.parseInt(strIndex);
            final boolean smooth = mSmoothSelectorView.getCheckedRadioButtonId() == R.id.rb_smooth_scroll;
            if (smooth) {
                mView.smoothScrollToPositionWithOffset(index, DOCK_OFFSET);
            } else {
                mView.scrollToPositionWithOffset(index, DOCK_OFFSET);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "index: " + strIndex + " is invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private TextView createSimpleTextView(String text) {
        TextView view = new TextView(this);
        view.setGravity(Gravity.CENTER);
        view.setText(text);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
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
            final AspectRatioLayout view = (AspectRatioLayout) getLayoutInflater().inflate(R.layout.item_imge_text, parent, false);
            view.addOnAttachStateChangeListener(mAttachStateListener);
            final ContentViewHolder holder = new ContentViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int top = v.getTop() - DOCK_OFFSET;
                    mView.smoothScrollBy(0, top);
                }
            });
            Logger.v(RecyclerViewActivity.this, "onCreateViewHolder", commonToString(holder), commonToString(view));
            return holder;
        }
    
        @Override
        public void onBindViewHolder(ContentViewHolder holder, int position) {
            Logger.d(RecyclerViewActivity.this, "onBindViewHolder", String.valueOf(position), commonToString(holder), commonToString(holder.itemView), "reuse = " + holder.itemView.getTag());

            holder.imageView.setImageResource(R.mipmap.ic_launcher);
            holder.textView.setText(data.get(position));
            holder.itemView.setTag("item " + position);
            holder.itemView.setBackgroundColor(position % 2 == 0 ? Color.LTGRAY : Color.WHITE);
            ((AspectRatioLayout) holder.itemView).setRatio(mockRatio(position));
        }

        private float mockRatio(int position) {
            final int index = position % 3;
            switch (index) {
                //case 0: return 0.56f;
                //case 1: return 1f;
                //case 2: return 1.78f;
                default: return 1.78f;
            }
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

        @Override
        public void onViewRecycled(ContentViewHolder holder) {
            super.onViewRecycled(holder);
            Logger.v(RecyclerViewActivity.this, "onViewRecycled", commonToString(holder), commonToString(holder.itemView), commonToString(holder.itemView.getTag()));
        }

        @Override
        public void onViewAttachedToWindow(ContentViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            Logger.v(RecyclerViewActivity.this, "onViewAttachedToWindow", commonToString(holder), commonToString(holder.itemView), commonToString(holder.itemView.getTag()));
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            super.onDetachedFromRecyclerView(recyclerView);
            Logger.v(RecyclerViewActivity.this, "onDetachedFromRecyclerView", commonToString(recyclerView));
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            Logger.v(RecyclerViewActivity.this, "onAttachedToRecyclerView");
        }

        @Override
        public void onViewDetachedFromWindow(ContentViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            Logger.v(RecyclerViewActivity.this, "onViewDetachedFromWindow", commonToString(holder), commonToString(holder.itemView), commonToString(holder.itemView.getTag()));
        }
    }
    
    private View.OnAttachStateChangeListener mAttachStateListener = new View.OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(View v) {
            Logger.i(RecyclerViewActivity.this, "onViewAttachedToWindow", commonToString(v), v.getTag());
        }
    
        @Override
        public void onViewDetachedFromWindow(View v) {
            Logger.w(RecyclerViewActivity.this, "onViewDetachedFromWindow", commonToString(v), v.getTag());
        }
    };
}
