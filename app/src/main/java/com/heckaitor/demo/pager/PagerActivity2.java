package com.heckaitor.demo.pager;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.heckaitor.demo.R;
import com.heckaitor.utils.log.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

public class PagerActivity2 extends AppCompatActivity {

    @BindView(R.id.rv_content)
    RecyclerView mRecyclerView;

    private ContentAdapter mAdapter;

    private int mCurrentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager2);
        ButterKnife.bind(this);

        mAdapter = new ContentAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL) {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                final int position = parent.getChildAdapterPosition(view);
                final int dividerWidth = 20;
                if (position == 0) {
                    outRect.left = dividerWidth;
                    outRect.right = dividerWidth >> 1;
                } else if (position == parent.getAdapter().getItemCount() - 1) {
                    outRect.left = dividerWidth >> 1;
                    outRect.right = dividerWidth;
                } else {
                    outRect.left = dividerWidth >> 1;
                    outRect.right = dividerWidth >> 1;
                }
                outRect.top = 0;
                outRect.bottom = 0;
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                final String state;
                switch (newState) {
                    case SCROLL_STATE_IDLE: state = "SCROLL_STATE_IDLE"; break;
                    case SCROLL_STATE_DRAGGING: state = "SCROLL_STATE_DRAGGING"; break;
                    case SCROLL_STATE_SETTLING: state = "SCROLL_STATE_SETTLING"; break;
                    default: state = "";
                }
                Logger.i(recyclerView, "onScrollStateChanged: " + state);
                checkPageChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Logger.v(recyclerView, "onScroll: " + dx + ", " + dy);
            }

            private void checkPageChanged(RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE) {
                    int index = 0;
                    final int count = recyclerView.getChildCount();
                    for (int i = 0; i < count; i++) {
                        final View itemView = recyclerView.getChildAt(i);
                        if (itemView != null
                                && itemView.getLeft() > 0
                                && itemView.getRight() < recyclerView.getWidth()) {
                            index = recyclerView.getChildAdapterPosition(itemView);
                            break;
                        }
                    }
                    if (index != mCurrentIndex) {
                        mCurrentIndex = index;
                        Logger.i(recyclerView, "onPageChanged = " + mCurrentIndex);
                    }
                }
            }
        });
    }

    private class ContentAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_round_image, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.textView.setText(position + "");
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_text);
        }
    }
}
