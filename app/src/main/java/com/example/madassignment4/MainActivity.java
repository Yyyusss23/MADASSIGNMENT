package com.example.madassignment4;

import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.example.madassignment4.Database.DatabaseHelper;

import androidx.core.graphics.Insets;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.close();

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_user_fragment);

        NavController navController = navHostFragment.getNavController();
        String userId = dbHelper.getUserIdByMostRecentLogin();
        if (userId != null) {
            String userLanguageId = dbHelper.getUserLanguage(userId);
            if (userLanguageId == null) {
                userLanguageId = "en"; // Default language
            }
            setLanguage(userLanguageId);
        } else {
            Log.e("MainActivity", "User ID is null");
        }
    }

    private void setLanguage(String languageId) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String languageCode = dbHelper.getLanguageName(languageId);
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}