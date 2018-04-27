package com.heckaitor.demo.popw;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by heckaitor on 2017/6/29.
 */
public class ExPopupWindow extends PopupWindow {
    
    @Override
    public void setContentView(View contentView) {
        if (contentView != null) {
            super.setContentView(contentView);
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);
            contentView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK: {
                            dismiss();
                            return true;
                        }
                        default: break;
                    }
                    
                    return false;
                }
            });
        }
    }
    
    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(touchable);
        if (touchable) {
            if (getBackground() == null) {
                super.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        } else {
            super.setBackgroundDrawable(null);
        }
    }
}
