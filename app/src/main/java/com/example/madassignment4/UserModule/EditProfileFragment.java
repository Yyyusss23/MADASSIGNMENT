package com.example.madassignment4.UserModule;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

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


public class EditProfileFragment extends Fragment {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private EditText ETUserHeight, ETUserWeight, ETUserGender, ETUserBirth;
    private Button BtnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Initialize DatabaseHelper and get writable database3
        dbHelper = new DatabaseHelper(requireContext());
        db = dbHelper.getWritableDatabase();

        // Initialize views
        ETUserHeight = view.findViewById(R.id.ETUserHeight);
        ETUserWeight = view.findViewById(R.id.ETUserWeight);
        ETUserGender = view.findViewById(R.id.ETUserGender);
        ETUserBirth = view.findViewById(R.id.ETUserBirth);
        BtnSave = view.findViewById(R.id.BtnSave);


        View.OnClickListener Save = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  saveOrUpdateProfile();
                Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_userProfileFragment);
            }
        };
        BtnSave.setOnClickListener(Save);

        return view;
    }

    private void saveOrUpdateProfile() {
        String height = ETUserHeight.getText().toString();
        String weight = ETUserWeight.getText().toString();
        String gender = ETUserGender.getText().toString();
        String yearOfBirth = ETUserBirth.getText().toString();

        // Validate input
        if (height.isEmpty() || weight.isEmpty() || gender.isEmpty() || yearOfBirth.isEmpty()) {
            Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse inputs
        String userId = dbHelper.getUserIdByMostRecentLogin();
        double parsedHeight = Double.parseDouble(height);
        double parsedWeight = Double.parseDouble(weight);
        int parsedYearOfBirth = Integer.parseInt(yearOfBirth);

        // Save or update profile
        boolean success = dbHelper.saveOrUpdateUserProfile(userId, gender, parsedHeight, parsedWeight, parsedYearOfBirth);
        if (success) {
            Toast.makeText(getContext(), "Profile saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error saving profile.", Toast.LENGTH_SHORT).show();
        }
    }
}
