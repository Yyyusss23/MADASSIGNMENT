package com.example.madassignment4.UserModule;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;

import java.util.Calendar;
import java.util.Map;


public class UserProfileFragment extends Fragment {

    private TextView userHeight, userWeight, userGender, userBirth, userAge;
    private Button editButton;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Initialize DatabaseHelper inside onCreateView, not the constructor
        dbHelper = new DatabaseHelper(requireContext());

        // Reference to the TextView in the Fragment layout
        TextView usernameTextView = view.findViewById(R.id.textView7);
        String username = dbHelper.getUsername();

        if (username != null) {
            usernameTextView.setText(username);
        } else {
            usernameTextView.setText("User");
        }

        // Initialize views
        userHeight = view.findViewById(R.id.UserHeight);
        userWeight = view.findViewById(R.id.UserWeight);
        userGender = view.findViewById(R.id.userGender);
        userBirth = view.findViewById(R.id.userBirth);
        editButton = view.findViewById(R.id.BtnEdit);
        userAge = view.findViewById(R.id.textView12);


        // Check and update profile data
        updateProfileViews();

        // Set up Edit button
        editButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_editProfileFragment);
            Toast.makeText(getContext(), "Navigate to edit profile!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void updateProfileViews() {
        String userId = dbHelper.getUserIdByMostRecentLogin();
        Map<String, Object> profileData = dbHelper.getUserProfile(userId);

        if (!profileData.isEmpty()) {
            // Profile data exists, populate the TextViews
            double height = (double) profileData.get(DatabaseHelper.COLUMN_HEIGHT);
            double weight = (double) profileData.get(DatabaseHelper.COLUMN_WEIGHT);
            String gender = (String) profileData.get(DatabaseHelper.COLUMN_GENDER);
            int yearOfBirth = (int) profileData.get(DatabaseHelper.COLUMN_YEAR_OF_BIRTH);

            // Calculate current age
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int age = currentYear - yearOfBirth;

            // Update TextViews
            userHeight.setText(String.format(String.valueOf(height))+ "cm");
            userWeight.setText(String.format(String.valueOf(weight))+" kg");
            userGender.setText(String.format(gender));
            userBirth.setText(String.format(String.valueOf(yearOfBirth)));
            userAge.setText(String.format(String.valueOf(age)+" years old"));
        } else {
            userHeight.setText("");
            userWeight.setText("");
            userGender.setText("");
            userBirth.setText("");
            userAge.setText("");
        }
    }


}
