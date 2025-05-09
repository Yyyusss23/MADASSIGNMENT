package com.example.madassignment4.UserModule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;

public class UserHomeFragment extends Fragment {
//
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewGreeting = view.findViewById(R.id.textViewGreeting);
        RadioButton radioActive = view.findViewById(R.id.radioButton1);
        RadioButton radioSick = view.findViewById(R.id.radioButton2);
        RadioButton radioBreak = view.findViewById(R.id.radioButton3);
        RadioButton radioInjured = view.findViewById(R.id.radioButton4);
        Button btnSave = view.findViewById(R.id.btnSaveHealthStatus);
        Button btnFood = view.findViewById(R.id.btnGetStarted);

        // Access the database helper
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        dbHelper.populateHealthStatusTable();

        // Retrieve and display the username
        String username = dbHelper.getUsername();
        textViewGreeting.setText(username != null ? "Hi, " + username + "!" : "Hi, User!");

        // Retrieve and set the saved health status33
        String userId = dbHelper.getUserIdByMostRecentLogin();
        String savedStatus = dbHelper.getUserHealthStatus(userId);

        if (savedStatus != null) {
            switch (savedStatus) {
                case "Active":
                    radioActive.setChecked(true);
                    break;
                case "Sick":
                    radioSick.setChecked(true);
                    break;
                case "Take a Break":
                    radioBreak.setChecked(true);
                    break;
                case "Injured":
                    radioInjured.setChecked(true);
                    break;
            }
        }

        // Set up navigation to Food Activity
        btnFood.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_userHomeFragment2_to_foodActivity)
        );

        // Save health status on button click
        btnSave.setOnClickListener(v -> {
            String selectedStatus = null;
            if (radioActive.isChecked()) {
                selectedStatus = "Active";
            } else if (radioSick.isChecked()) {
                selectedStatus = "Sick";
            } else if (radioBreak.isChecked()) {
                selectedStatus = "Take a Break";
            } else if (radioInjured.isChecked()) {
                selectedStatus = "Injured";
            }

            if (selectedStatus != null) {
                dbHelper.saveOrUpdateUserHealthStatus(userId, selectedStatus);
                Toast.makeText(requireContext(), "Health status saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Please select a health status.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_home, container, false);
    }
}
