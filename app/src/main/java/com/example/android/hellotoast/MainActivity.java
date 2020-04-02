/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.hellotoast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Displays two Buttons and a TextView.
 * - Pressing the TOAST button shows a Toast.
 * - Pressing the COUNT button increases the displayed mCount.
 *
 * Note: Fixing behavior when device is rotated is a challenge exercise for the student.
 */

public class MainActivity extends AppCompatActivity {

    public static int mCount = 0;
    private TextView mShowCount;
    public static int mColor = R.string.black;

    // Key for current count
    public final static String COUNT_KEY = "count";
    // Key for current color
    public final static String COLOR_KEY = "color";
    public final static String AUTOSAVE_KEY = "autosave";

    public static SharedPreferences mPreferences;
    public static String sharedPrefFile =
            "com.example.android.hellotoast";

    private void initializeFromPreferences() {
        mCount = mPreferences.getInt(COUNT_KEY, 0);
        mShowCount.setText(String.format("%s", mCount));
        mColor = mPreferences.getInt(COLOR_KEY, R.string.black);
        mShowCount.setBackgroundColor(getColorFromResource(mColor));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        initializeFromPreferences();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        initializeFromPreferences();
    }

    public void savePreferences() {
        SharedPreferences.Editor preferencesEditor = MainActivity.mPreferences.edit();
        preferencesEditor.putInt(MainActivity.COUNT_KEY, MainActivity.mCount);
        preferencesEditor.putInt(MainActivity.COLOR_KEY, MainActivity.mColor);
        preferencesEditor.putBoolean(MainActivity.AUTOSAVE_KEY, SettingsActivity.mToggle.isChecked());
        preferencesEditor.apply();
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    /*
     * Increments the number in the TextView when the COUNT button is clicked.
     *
     * @param view The view that triggered this onClick handler.
     *             Since the count always appears in the TextView,
     *             the passed in view is not used.
     */

    public void countUp(View view) {
        mCount++;
        if (mShowCount != null)
            mShowCount.setText(Integer.toString(mCount));
    }

    public void openSettings(View view) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    public int getColorFromResource(int resource) {
        switch(resource) {
            case R.string.black:
                return Color.BLACK;
            case R.string.blue:
                return Color.BLUE;
            case R.string.red:
                return Color.RED;
            case R.string.green:
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }

    public void changeBackground(View view) {
        mColor = mPreferences.getInt(COLOR_KEY, R.string.black);
        mShowCount.setBackgroundColor(getColorFromResource(mColor));
    }
}
