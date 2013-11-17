package ru.tulupov.nsuconnect.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {

    private boolean enabled = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (onPagingListener != null && onPagingListener.allowPaging()) {
            return super.onTouchEvent(event);
        }

        if (enabled) {
            return super.onTouchEvent(event);
        }


        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (onPagingListener != null && onPagingListener.allowPaging()) {
            return super.onInterceptTouchEvent(event);
        }
        if (enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private OnPagingListener onPagingListener;

    public void setOnPagingListener(OnPagingListener onPagingListener) {
        this.onPagingListener = onPagingListener;
    }

    public interface OnPagingListener {
        boolean allowPaging();
    }
}