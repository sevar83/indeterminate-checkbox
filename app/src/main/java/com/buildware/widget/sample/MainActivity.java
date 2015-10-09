package com.buildware.widget.sample;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.buildware.widget.indeterm.IndeterminateCheckBox;
import com.buildware.widget.indeterm.IndeterminateCheckedTextView;
import com.buildware.widget.indeterm.IndeterminateRadioButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final IndeterminateCheckBox checkBox = (IndeterminateCheckBox) findViewById(R.id.indeterm_checkbox);
        checkBox.setOnStateChangedListener(new IndeterminateCheckBox.OnStateChangedListener() {
            @Override
            public void onStateChanged(IndeterminateCheckBox check, @Nullable Boolean state) {
                if (state == null) {
                    check.setText("Indeterminate state");
                } else {
                    check.setText(state ? "Checked state" : "Unchecked state");
                }
            }
        });

        final IndeterminateCheckedTextView checkedTextView = (IndeterminateCheckedTextView) findViewById(R.id.indeterm_checkedtextview);
        checkedTextView.setOnStateChangedListener(new IndeterminateCheckedTextView.OnStateChangedListener() {
            @Override
            public void onStateChanged(IndeterminateCheckedTextView check, @Nullable Boolean state) {
                if (state == null) {
                    check.setText("Indeterminate state");
                } else {
                    check.setText(state ? "Checked state" : "Unchecked state");
                }
            }
        });

        final IndeterminateRadioButton radio = (IndeterminateRadioButton) findViewById(R.id.indeterm_radio);
        radio.setOnStateChangedListener(new IndeterminateRadioButton.OnStateChangedListener() {
            @Override
            public void onStateChanged(IndeterminateRadioButton check, @Nullable Boolean state) {
                if (state == null) {
                    radio.setText("Indeterminate state");
                } else {
                    radio.setText(state ? "Checked state" : "Unchecked state");
                }
            }
        });

        findViewById(R.id.btn_indeterminate1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setState(null);
            }
        });

        findViewById(R.id.btn_checked1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(true);
            }
        });

        findViewById(R.id.btn_unchecked1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(false);
            }
        });

        findViewById(R.id.btn_indeterminate2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedTextView.setState(null);
            }
        });

        findViewById(R.id.btn_checked2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedTextView.setChecked(true);
            }
        });

        findViewById(R.id.btn_unchecked2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedTextView.setChecked(false);
            }
        });

        findViewById(R.id.btn_indeterminate3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio.setState(null);
            }
        });

        findViewById(R.id.btn_checked3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio.setChecked(true);
            }
        });

        findViewById(R.id.btn_unchecked3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio.setChecked(false);
            }
        });
    }
}
