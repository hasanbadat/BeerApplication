package com.hasan.beerapp;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {

    // Stores the starting X position of the ACTION_DOWN event
    float downX;
    private boolean enabled;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {

            boolean wasSwipeToRight = this.wasSwipeToRightEvent(event);
            return super.onInterceptTouchEvent(event);

        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    /**
     * Checks the X position value of the event and compares it to
     * previous MotionEvents. Returns a true/false value based on if the
     * event was an swipe to the right or a swipe to the left.
     *
     * @param event -   Motion Event triggered by the ViewPager
     * @return -   True if the swipe was from left to right. False otherwise
     */
    private boolean wasSwipeToRightEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                return false;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                return downX - event.getX() > 0;

            default:
                return false;
        }
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}