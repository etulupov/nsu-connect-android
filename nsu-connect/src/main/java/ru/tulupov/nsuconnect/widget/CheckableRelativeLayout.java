package ru.tulupov.nsuconnect.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.RelativeLayout;


public  class CheckableRelativeLayout extends RelativeLayout implements Checkable {
    private boolean mChecked;

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    public CheckableRelativeLayout(Context context) {
        this(context, null);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void toggle() {
        setChecked(!mChecked);
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
        }
    }




    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (getBackground() != null) {
            int[] myDrawableState = getDrawableState();


            getBackground() .setState(myDrawableState);

            invalidate();
        }
    }

}