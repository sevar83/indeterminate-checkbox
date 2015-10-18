package com.buildware.widget.sample;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.buildware.widget.indeterm.IndeterminateCheckBox;
import com.buildware.widget.indeterm.IndeterminateRadioButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        final AppCompatCheckBox appCompatCheckBox = (AppCompatCheckBox) findViewById(R.id.app_compat_checkbox);
        final IndeterminateCheckBox indetermCheckBox = (IndeterminateCheckBox) findViewById(R.id.indeterm_checkbox);
        final IndeterminateRadioButton radio = (IndeterminateRadioButton) findViewById(R.id.indeterm_radio);

        indetermCheckBox.setOnStateChangedListener(new IndeterminateCheckBox.OnStateChangedListener() {
            @Override
            public void onStateChanged(IndeterminateCheckBox check, @Nullable Boolean state) {
                String stateText = (state != null) ? (state ? "Checked" : "Unchecked") : "Indeterminate";
                Toast.makeText(MainActivity.this, "IndeterminateCheckBox: " + stateText, Toast.LENGTH_SHORT).show();
            }
        });

        radio.setOnStateChangedListener(new IndeterminateRadioButton.OnStateChangedListener() {
            @Override
            public void onStateChanged(IndeterminateRadioButton radioButton, @Nullable Boolean state) {
                String stateText = (state != null) ? (state ? "Checked" : "Unchecked") : "Indeterminate";
                Toast.makeText(MainActivity.this, "IndeterminateRadioButton: " + stateText, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_indeterminate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indetermCheckBox.setState(null);
                radio.setState(null);
            }
        });

        findViewById(R.id.btn_checked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(true);
                appCompatCheckBox.setChecked(true);
                indetermCheckBox.setChecked(true);
                radio.setChecked(true);
            }
        });

        findViewById(R.id.btn_unchecked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(false);
                appCompatCheckBox.setChecked(false);
                indetermCheckBox.setChecked(false);
                radio.setChecked(false);
            }
        });

        findViewById(R.id.btn_enable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setEnabled(true);
                appCompatCheckBox.setEnabled(true);
                indetermCheckBox.setEnabled(true);
                radio.setEnabled(true);
            }
        });

        findViewById(R.id.btn_disable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setEnabled(false);
                appCompatCheckBox.setEnabled(false);
                indetermCheckBox.setEnabled(false);
                radio.setEnabled(false);
            }
        });
    }
}
