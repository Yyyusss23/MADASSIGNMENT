package com.example.madassignment4.UserModule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;

public class OpeningFragment extends Fragment {

    private EditText editTextUsername;
    private Button buttonSave;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opening, container, false);

        // Initialize database helper
        dbHelper = new DatabaseHelper(getContext());

        // Find views
        editTextUsername = view.findViewById(R.id.nameInput);
        buttonSave = view.findViewById(R.id.saveButton);

        buttonSave.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            if (!username.isEmpty()) {
                // Save or update user in the database
                dbHelper.saveOrUpdateUser(username);

                // Save username to SharedPreferences
                SharedPreferences preferences = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user_name", username);
                editor.apply();

                Toast.makeText(getContext(), "Username saved: " + username, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(),UserAcitivity.class);
                intent.putExtra("username", username); // Pass username to UserActivity
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}