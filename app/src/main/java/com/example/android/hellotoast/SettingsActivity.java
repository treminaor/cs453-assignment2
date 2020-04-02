package com.example.android.hellotoast;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.android.hellotoast.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    String[] colorArray;
    Spinner mSpinner;
    public static ToggleButton mToggle;
    public static Boolean toggleEx;
    public static SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mPreferences = getSharedPreferences(MainActivity.sharedPrefFile, MODE_PRIVATE);

        mSpinner = (Spinner) findViewById(R.id.spinner_color);
        colorArray = new String[]{getString(R.string.black), getString(R.string.blue), getString(R.string.red), getString(R.string.green)};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colorArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        mSpinner.setAdapter(spinnerArrayAdapter);

        mToggle = (ToggleButton) findViewById((R.id.autoSaveToggle));
        toggleEx = MainActivity.mPreferences.getBoolean(MainActivity.AUTOSAVE_KEY, false);
        mToggle.setChecked(toggleEx);

        int loadedColor = MainActivity.mPreferences.getInt(MainActivity.COLOR_KEY, MainActivity.mColor);
        int currentColorIndex = Arrays.asList(colorArray).indexOf(getString(loadedColor));
        mSpinner.setSelection(currentColorIndex);
    }

    public void savePreferences(View view) {
        int currentColorIndex = Arrays.asList(colorArray).indexOf(mSpinner.getSelectedItem());

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(MainActivity.COUNT_KEY, MainActivity.mCount);
        preferencesEditor.putInt(MainActivity.COLOR_KEY, getResourceFromColor(mSpinner.getSelectedItem().toString()));
        preferencesEditor.putBoolean(MainActivity.AUTOSAVE_KEY, mToggle.isChecked());
        preferencesEditor.apply();

        showToast("Preferences Saved");
    }

    public int getResourceFromColor(String color) {
        switch(color) {
            case "Black":
                return R.string.black;
            case "Blue":
                return R.string.blue;
            case "Red":
                return R.string.red;
            case "Green":
                return R.string.green;
            default:
                return Color.BLACK;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void resetPreferences(View view) {
        mPreferences.edit().clear().commit();
        showToast("Preferences Reset");
    }

    public void showToast(String message)
    {
        Toast toast = Toast.makeText(this, message,
                Toast.LENGTH_SHORT);
        toast.show();
    }
}
