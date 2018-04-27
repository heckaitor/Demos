package com.heckaitor.utils.log;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.heckaitor.utils.R;

import java.text.SimpleDateFormat;

import static com.heckaitor.utils.log.Logger.VERBOSE;
import static com.heckaitor.utils.log.Logger.DEBUG;
import static com.heckaitor.utils.log.Logger.INFO;
import static com.heckaitor.utils.log.Logger.WARN;
import static com.heckaitor.utils.log.Logger.ERROR;

public class WindowLog extends LogNode {
    
    private WindowLog() { }
    
    @Override
    public LogNodeFactory.LoggerType getType() {
        return LogNodeFactory.LoggerType.WINDOW;
    }
    
    public static WindowLog create(Context context) {
        WindowLog windowNode = new WindowLog();
    
        windowNode.mView = windowNode.generateLogView(context);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        
        Point size = new Point();
        manager.getDefaultDisplay().getSize(size);
        final int width = size.x;
        final int height = size.y / 4;
        
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                width, height,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 100;
        manager.addView(windowNode.mView, params);
        
        return windowNode;
    }
    
    @Override
    public void destroySelf() {
        if (mView != null) {
            WindowManager manager = (WindowManager) mView.getContext().getSystemService(Context.WINDOW_SERVICE);
            manager.removeView(mView);
            mView = null;
            mLogView = null;
        }
    }
    
    private void updatePosition(Context context, int offsetY) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) mView.getLayoutParams();
        params.y += offsetY;
        manager.updateViewLayout(mView, params);
    }
    
    private View mView;
    private TextView mLogView;
    
    private SimpleDateFormat mFormatter;
    
    @Override
    public void print(int priority, String tag, String message) {
        if (mLogView == null) {
            return;
        }
    
        SpannableStringBuilder builder = new SpannableStringBuilder("\n");
        builder.append(currentTimeString()).append(" ");
        appendNotNull(builder, tag);
        builder.append(": ");
        appendNotNull(builder, message);
        
        final int length = builder.length();
        final int color = makeColor(priority);
        builder.setSpan(new ForegroundColorSpan(color), 0, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        
        mLogView.append(builder);
    }
    
    private String currentTimeString() {
        if (mFormatter == null) {
            mFormatter = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        }
        return mFormatter.format(System.currentTimeMillis());
    }
    
    private void appendNotNull(SpannableStringBuilder builder, String text) {
        if (!TextUtils.isEmpty(text)) {
            builder.append(text);
        }
    }
    
    private int makeColor(int priority) {
        switch (priority) {
            case VERBOSE: return Color.LTGRAY;
            case DEBUG:   return Color.BLACK;
            case INFO:    return Color.GREEN;
            case WARN:    return Color.YELLOW;
            case ERROR:   return Color.RED;
            default: return Color.BLACK;
        }
    }
    
    private View generateLogView(Context context) {
        final View view = LayoutInflater.from(context).inflate(R.layout.win_log_layout, null, false);
        final ScrollView scrollView = view.findViewById(R.id.sv_log_holder);
        mLogView = scrollView.findViewById(R.id.tv_log_content);
        mLogView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            
            @Override
            public void afterTextChanged(Editable editable) {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        view.findViewById(R.id.ll_control_bar).setOnTouchListener(new View.OnTouchListener() {
    
            private int mPreviousY;
            
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int action = event.getAction();
                final int y = (int) event.getRawY();
                switch (action) {
                    case MotionEvent.ACTION_MOVE: {
                        updatePosition(view.getContext(), y - mPreviousY);
                        break;
                    }
                    default: break;
                }
                
                mPreviousY = y;
                return true;
            }
        });
        
        return view;
    }
}
