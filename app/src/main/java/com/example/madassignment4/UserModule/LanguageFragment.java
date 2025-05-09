package com.example.madassignment4.UserModule;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;

import java.util.Locale;

public class LanguageFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private RadioGroup radioGroupLanguage;
    private Button changeLanguageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        dbHelper = new DatabaseHelper(getContext());
        radioGroupLanguage = view.findViewById(R.id.radioGroup_language);
        changeLanguageButton = view.findViewById(R.id.button_change_language);

        String userId = dbHelper.getUserIdByMostRecentLogin();

        // Check if the userId is valid
        if (userId != null) {
            String userLanguageId = dbHelper.getUserLanguage(userId);

            // Set the language based on the stored preference###3
            if (userLanguageId == null) {
                userLanguageId = "en"; // Default to English if no preference is found
            }
            setLanguage(userLanguageId);
        } else {
            Log.e("LanguageFragment", "User ID is null");
        }

        // Button click listener for language change
        changeLanguageButton.setOnClickListener(v -> {
            int selectedId = radioGroupLanguage.getCheckedRadioButtonId();
            String newLanguageId = "en"; // Default to English

            if (selectedId == R.id.radio_chinese) {
                newLanguageId = "zh"; // Chinese
            } else if (selectedId == R.id.radio_malay) {
                newLanguageId = "ms"; // Malay
            } else if (selectedId == R.id.radio_hindi) {
                newLanguageId = "hi"; // Hindi
            }

            if (userId != null) {
                dbHelper.insertLanguageSetting(userId, newLanguageId);
                setLanguage(newLanguageId);
                // Force reload the activity to apply language change
                getActivity().recreate();
            } else {
                Log.e("LanguageFragment", "User ID is null during language change");
            }
        });

        return view;
    }

    // Method to update the language of the app based on the language ID
    private void setLanguage(String languageId) {
        String languageCode = dbHelper.getLanguageName(languageId);
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
