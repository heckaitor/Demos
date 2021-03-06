package com.heckaitor.demo.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.heckaitor.demo.R;
import com.heckaitor.demo.util.StringUtils;
import com.heckaitor.utils.log.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * ListView Demo
 */
public class ListViewActivity extends AppCompatActivity {

    private ListView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        mView = (ListView) findViewById(R.id.lv_content);
        mView.setAdapter(new ContentAdapter());

        mView.setOnScrollListener(mListener);

        final long anchor = System.nanoTime();
        hookListViewOnScrollListener();
        Logger.w(this, (System.nanoTime() - anchor)/1000000 + "ms");
    }

    private void hookListViewOnScrollListener() {
        try {
            Field field = AbsListView.class.getDeclaredField("mOnScrollListener");
            field.setAccessible(true);
            AbsListView.OnScrollListener o = (AbsListView.OnScrollListener) field.get(mView);
            Logger.d(o);
            mView.setOnScrollListener(new OnScrollListenerProxy(o));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private AbsListView.OnScrollListener mListener = new AbsListView.OnScrollListener() {

        private int mOldFirstVisibleItem;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //Logger.i(this, "origin", "onScrollStateChanged", scrollState2String(scrollState));
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem != mOldFirstVisibleItem) {
                //Logger.v(this, "origin", "onScroll", "first = " + firstVisibleItem);
                mOldFirstVisibleItem = firstVisibleItem;
            }
        }
    };

    private static class OnScrollListenerProxy implements AbsListView.OnScrollListener {

        private AbsListView.OnScrollListener mOrigin;
        private int mOldFirstVisibleItem;

        public OnScrollListenerProxy(AbsListView.OnScrollListener object) {
            this.mOrigin = object;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (mOrigin != null) {
                mOrigin.onScrollStateChanged(view, scrollState);
            }
            //Logger.i(this, "proxy", "onScrollStateChanged", scrollState2String(scrollState));
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (mOrigin != null) {
                mOrigin.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
            if (firstVisibleItem != mOldFirstVisibleItem) {
                //Logger.v(this, "proxy", "onScroll", "first = " + firstVisibleItem);
                mOldFirstVisibleItem = firstVisibleItem;
            }
        }
    }

    private class ContentAdapter extends BaseAdapter {

        private List<String> data;

        public ContentAdapter() {
            final int size = 30;
            data = new ArrayList<>(30);
            for (int i = 0; i < size; i++) {
                data.add("Item " + i);
            }
        }

        @Override
        public int getCount() {
            return data != null ? data.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return data != null ? data.get(position) : "";
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final String tag = convertView != null ? (String) convertView.getTag(R.id.view_tag) : "null";
            Logger.d(ListViewActivity.this, "getView", String.valueOf(position), "reuse = " + StringUtils.commonToString(convertView) + ", " + tag);

            ContentViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_imge_text, parent, false);
                convertView.addOnAttachStateChangeListener(mAttachStateListener);
                holder = new ContentViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ContentViewHolder) convertView.getTag();
            }

            holder.imageView.setImageResource(R.mipmap.ic_launcher);
            holder.textView.setText(data.get(position));
            convertView.setTag(R.id.view_tag, data.get(position));

            return convertView;
        }

        class ContentViewHolder{
            ImageView imageView;
            TextView textView;

            public ContentViewHolder(View view) {
                imageView = (ImageView) view.findViewById(R.id.iv_image);
                textView = (TextView) view.findViewById(R.id.tv_text);
            }
        }

        private View.OnAttachStateChangeListener mAttachStateListener = new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                Logger.v(ListViewActivity.this, "onViewAttachedToWindow", StringUtils.commonToString(v), v.getTag(R.id.view_tag));
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                Logger.w(ListViewActivity.this, "onViewDetachedFromWindow", StringUtils.commonToString(v), v.getTag(R.id.view_tag));
            }
        };
    }
}
