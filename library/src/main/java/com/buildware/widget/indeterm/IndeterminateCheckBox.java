package com.buildware.widget.indeterm;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.ViewDebug;

/**
 * Created by Svetlozar Kostadinov on 15-10-6.
 */
public class IndeterminateCheckBox extends AppCompatCheckBox
        implements IndeterminateCheckable {

    private static final int[] INDETERMINATE_STATE_SET = {
            R.attr.state_indeterminate
    };

    private Boolean mState;
    private boolean mBroadcasting;
    private OnStateChangedListener mOnStateChangedListener;

    /**
     * Interface definition for a callback to be invoked when the checked state changed.
     */
    public interface OnStateChangedListener {
        /**
         * Called when the indeterminate state has changed.
         *
         * @param buttonView The checkbox view whose state has changed.
         * @param state The state of buttonView. Value meanings:
         *              null = indeterminate state
         *              true = checked state
         *              false = unchecked state
         */
        void onStateChanged(IndeterminateCheckBox buttonView, @Nullable Boolean state);
    }

    public IndeterminateCheckBox(Context context) {
        this(context, null);
    }

    public IndeterminateCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.checkboxStyle);
    }

    public IndeterminateCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setButtonDrawable(R.drawable.btn_checkmark);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndeterminateCheckable);
        try {
            // Read the XML attributes
            final boolean indeterminate = a.getBoolean(
                    R.styleable.IndeterminateCheckable_indeterminate, false);
            if (indeterminate) {
                setState(null);
            }
        } finally {
            a.recycle();
        }
    }

    @Override
    public void toggle() {
        if (mState == null) {
            setChecked(true);
        } else {
            super.toggle();
        }
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        setState(checked);
    }

    public void setState(Boolean state) {
        if (mState != state) {
            mState = state;
            refreshDrawableState();
            /*notifyViewAccessibilityStateChangedIfNeeded(
                    AccessibilityEvent.CONTENT_CHANGE_TYPE_UNDEFINED); */

            // Avoid infinite recursions if setState() is called from a listener
            if (mBroadcasting) {
                return;
            }

            mBroadcasting = true;
            if (mOnStateChangedListener != null) {
                mOnStateChangedListener.onStateChanged(this, mState);
            }

            mBroadcasting = false;
        }
    }

    @ViewDebug.ExportedProperty
    public Boolean getState() {
        return mState;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (getState() == null) {
            mergeDrawableStates(drawableState, INDETERMINATE_STATE_SET);
        }
        return drawableState;
    }

    /**
     * Register a callback to be invoked when the indeterminate state changes.
     *
     * @param listener the callback to call on indeterminate state change
     */
    public void setOnStateChangedListener(OnStateChangedListener listener) {
        mOnStateChangedListener = listener;
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return IndeterminateCheckBox.class.getName();
    }

    static class SavedState extends BaseSavedState {
        Boolean indeterminate;

        /**
         * Constructor called from {@link IndeterminateCheckBox#onSaveInstanceState()}
         */
        SavedState(Parcelable superState) {
            super(superState);
        }

        /**
         * Constructor called from {@link #CREATOR}
         */
        private SavedState(Parcel in) {
            super(in);
            indeterminate = (Boolean)in.readValue(null);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(indeterminate);
        }

        @Override
        public String toString() {
            return "IndeterminateCheckBox.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " indeterminate=" + indeterminate + "}";
        }

        public static final Creator<SavedState> CREATOR
                = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);

        ss.indeterminate = getState();
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;

        super.onRestoreInstanceState(ss.getSuperState());
        setState(ss.indeterminate);
        requestLayout();
    }
}
