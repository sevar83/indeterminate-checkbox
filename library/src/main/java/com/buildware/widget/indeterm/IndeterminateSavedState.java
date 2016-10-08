package com.buildware.widget.indeterm;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by sevar on 08.10.16.
 */
class IndeterminateSavedState extends View.BaseSavedState {
    boolean indeterminate;

    /**
     * Constructor called from {@link IndeterminateRadioButton#onSaveInstanceState()}
     */
    IndeterminateSavedState(Parcelable superState) {
        super(superState);
    }

    /**
     * Constructor called from {@link #CREATOR}
     */
    private IndeterminateSavedState(Parcel in) {
        super(in);
        indeterminate = in.readInt() != 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(indeterminate ? 1 : 0);
    }

    @Override
    public String toString() {
        return "IndetermSavedState.SavedState{"
                + Integer.toHexString(System.identityHashCode(this))
                + " indeterminate=" + indeterminate + "}";
    }

    public static final Creator<IndeterminateSavedState> CREATOR
            = new Creator<IndeterminateSavedState>() {
        public IndeterminateSavedState createFromParcel(Parcel in) {
            return new IndeterminateSavedState(in);
        }

        public IndeterminateSavedState[] newArray(int size) {
            return new IndeterminateSavedState[size];
        }
    };
}
