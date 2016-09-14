package com.example.android4noobs.programmaticallychangelanguage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getName();
    @Nullable
    TextView textView = null;
    @Nullable
    Button btnRuView = null;
    @Nullable
    Button btnEnView = null;
    @Nullable
    Button btnDeView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            initViews();
            Log.i(TAG, "Initializing views successful");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error, not initialized views");
        }
        try {
            loadLocaleFromSharedPreferences();
            Log.i(TAG, "Load data from SharedPreferences successful");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error, not loaded data from SharedPreferences");
        }

        /**
         * Log default language on device
         */
        Log.i(TAG, Locale.getDefault().getDisplayLanguage() + "is default languages on devise");
    }

    @Override
    public void onClick(View view) {
        String language = "en";
        switch (view.getId()) {
            case R.id.btn_ru:
                language = "ru";
                Toast.makeText(this, "Russian", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Change language to ru successful");
                break;
            case R.id.btn_en:
                language = "en";
                Toast.makeText(this, "English", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Change language to en successful");
                break;
            case R.id.btn_de:
                language = "de";
                Toast.makeText(this, "German", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Change language to de successful");
                break;
            default:
                Log.e(TAG, "Error in OnClickListener");
                break;
        }
        try {
            changeLanguage(language);
            Log.i(TAG, "Change language successful on click");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error, not changed languages");
        }
    }


    private void changeLanguage(String language) {
        if (language.equalsIgnoreCase("")) {
            return;
        }
        Locale locale = new Locale(language);
        saveLocaleToSharedPreferences(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        getBaseContext().getResources()
                .updateConfiguration(configuration, getBaseContext()
                        .getResources().getDisplayMetrics());

        try {
            updateTextOnViews();
            Log.i(TAG, "Update text on view successful");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error, not updated text on views");
        }
    }

    public void saveLocaleToSharedPreferences(String language) {
        /**
         * Save data languages from SharedPreferences
         */
        String languagePreferences = "Language";
        SharedPreferences sharedPreferences = getSharedPreferences("StringKey", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(languagePreferences, language);
        editor.apply();
    }

    public void loadLocaleFromSharedPreferences() {
        /**
         * Load data languages from SharedPreferences
         */
        String languagePreferences = "Language";
        SharedPreferences sharedPreferences = getSharedPreferences("StringKey", Activity.MODE_PRIVATE);
        String language = sharedPreferences.getString(languagePreferences, "");
        try {
            changeLanguage(language);
            Log.i(TAG, "Change language successful");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error, not changed language");
        }
    }

    private void updateTextOnViews() {
        /**
         * Update all views text
         */
        if (textView != null) {
            textView.setText(R.string.text);
        }
        if (btnEnView != null) {
            btnEnView.setText(R.string.btn_en);
        }
        if (btnRuView != null) {
            btnRuView.setText(R.string.btn_ru);
        }
        if (btnDeView != null) {
            btnDeView.setText(R.string.btn_de);
        }
    }

    private void initViews() {
        /**
         * Initialized all views
         */
        textView = (TextView) findViewById(R.id.txt_view);
        btnRuView = (Button) findViewById(R.id.btn_ru);
        btnEnView = (Button) findViewById(R.id.btn_en);
        btnDeView = (Button) findViewById(R.id.btn_de);

        if (btnRuView != null) {
            btnRuView.setOnClickListener(this);
        }
        if (btnEnView != null) {
            btnEnView.setOnClickListener(this);
        }
        if (btnDeView != null) {
            btnDeView.setOnClickListener(this);
        }
    }
}
