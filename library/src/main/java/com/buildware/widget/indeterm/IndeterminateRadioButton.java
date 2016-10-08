package com.buildware.widget.indeterm;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.ViewDebug;

/**
 * A RadioButton with additional 3rd "indeterminate" state.
 * By default it is in "determinate" (checked or unchecked) state.
 * @author Svetlozar Kostadinov (sevarbg@gmail.com)
 */
public class IndeterminateRadioButton extends AppCompatRadioButton
        implements IndeterminateCheckable {

    private static final int[] INDETERMINATE_STATE_SET = {
            R.attr.state_indeterminate
    };

    private boolean mIndeterminate;
    private boolean mBroadcasting;
    private OnStateChangedListener mOnStateChangedListener;

    /**
     * Interface definition for a callback to be invoked when the checked state changed.
     */
    public interface OnStateChangedListener {
        /**
         * Called when the indeterminate state has changed.
         *
         * @param radioButton The RadioButton whose state has changed.
         * @param state The state of buttonView. Value meanings:
         *              null = indeterminate state
         *              true = checked state
         *              false = unchecked state
         */
        void onStateChanged(IndeterminateRadioButton radioButton, @Nullable Boolean state);
    }

    public IndeterminateRadioButton(Context context) {
        this(context, null);
    }

    public IndeterminateRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.radioButtonStyle);
    }

    public IndeterminateRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //setSupportButtonTintList(ContextCompat.getColorStateList(context, R.color.control_checkable_material));
        setButtonDrawable(Utils.tintDrawable(this, R.drawable.btn_radio));

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
        if (mIndeterminate) {
            setChecked(true);
        } else {
            super.toggle();
        }
    }

    public void setChecked(boolean checked) {
        final boolean checkedChanged = isChecked() != checked;
        super.setChecked(checked);
        final boolean wasIndeterminate = isIndeterminate();
        setIndeterminateImpl(false, false);
        if (wasIndeterminate || checkedChanged) {
            notifyStateListener();
        }
    }

    @ViewDebug.ExportedProperty
    public boolean isIndeterminate() {
        return mIndeterminate;
    }

    public void setIndeterminate(boolean indeterminate) {
        setIndeterminateImpl(indeterminate, true);
    }

    private void setIndeterminateImpl(boolean indeterminate, boolean notify) {
        if (mIndeterminate != indeterminate) {
            mIndeterminate = indeterminate;
            refreshDrawableState();
            if (notify) {
                /*notifyViewAccessibilityStateChangedIfNeeded(
                        AccessibilityEvent.CONTENT_CHANGE_TYPE_UNDEFINED); */
                notifyStateListener();
            }
        }
    }


    @ViewDebug.ExportedProperty
    public Boolean getState() {
        return mIndeterminate ? null : isChecked();
    }

    @Override
    public void setState(Boolean state) {
        if (state != null) {
            setChecked(state);
        } else {
            setIndeterminate(true);
        }
    }

    /**
     * Register a callback to be invoked when the indeterminate or checked state changes.
     * The standard <code>OnCheckedChangedListener</code> will still be called prior to
     * OnStateChangedListener.
     *
     * @param listener the callback to call on indeterminate or checked state change
     */
    public void setOnStateChangedListener(OnStateChangedListener listener) {
        mOnStateChangedListener = listener;
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return IndeterminateRadioButton.class.getName();
    }

    private void notifyStateListener() {
        // Avoid infinite recursions if state is changed from a listener
        if (mBroadcasting) {
            return;
        }

        mBroadcasting = true;
        if (mOnStateChangedListener != null) {
            mOnStateChangedListener.onStateChanged(this, getState());
        }
        mBroadcasting = false;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (getState() == null) {
            mergeDrawableStates(drawableState, INDETERMINATE_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        IndeterminateSavedState ss = new IndeterminateSavedState(superState);
        ss.indeterminate = mIndeterminate;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        IndeterminateSavedState ss = (IndeterminateSavedState) state;

        // This temporarily disallows our callback notification, we will call it below if needed
        mBroadcasting = true;
        super.onRestoreInstanceState(ss.getSuperState());
        mBroadcasting = false;

        mIndeterminate = ss.indeterminate;
        // Both "indeterminate" and "checked" state are considered "excited" states. "Excited" state
        // is state that is different from the default "unchecked". On view restoration CompoundButton
        // notifies for change if the restored state is non-default. So, we will do the same for our merged state.
        if (mIndeterminate || isChecked()) {
            notifyStateListener();
        }
    }
}
