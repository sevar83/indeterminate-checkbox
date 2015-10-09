package com.buildware.widget.indeterm;

import android.widget.Checkable;

/**
 * Extension to Checkable interface with addition "indeterminate" state
 * represented by <code>state</code> == null.
 */
public interface IndeterminateCheckable extends Checkable {

    void setState(Boolean state);
    Boolean getState();
}
