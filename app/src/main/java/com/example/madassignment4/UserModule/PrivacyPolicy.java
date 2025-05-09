package com.example.madassignment4.UserModule;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class PrivacyPolicy extends Fragment {

    private TextView textViewPrivacyPolicy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        textViewPrivacyPolicy = view.findViewById(R.id.textView2);
        initializePrivacyPolicy();
        loadPrivacyPolicy();
        return view;
    }//

    private void initializePrivacyPolicy() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Cursor cursor = dbHelper.getPrivacyPolicy();

        if (cursor == null || !cursor.moveToFirst()) {
            dbHelper.saveOrUpdatePrivacyPolicy("1", getString(R.string.privacy_policy_content));
        } else {
            String existingContent = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRIVACY_POLICY_CONTENT));
            String newContent = getString(R.string.privacy_policy_content);

            if (!existingContent.equals(newContent)) {
                dbHelper.saveOrUpdatePrivacyPolicy("1", newContent);
            }
        }

        if (cursor != null) cursor.close();
    }

    private void loadPrivacyPolicy() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Cursor cursor = dbHelper.getPrivacyPolicy();

        if (cursor != null && cursor.moveToFirst()) {
            String privacyPolicyContent = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRIVACY_POLICY_CONTENT));
            String lastUpdated = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRIVACY_POLICY_LAST_UPDATED));

            textViewPrivacyPolicy.setText(privacyPolicyContent + "\nLast Updated: " + lastUpdated);
        }

        if (cursor != null) cursor.close();
    }
}
