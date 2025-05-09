package com.example.madassignment4.UserModule;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;


public class ProfileFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Reference to the TextView in the Fragment layout
        TextView usernameTextView = view.findViewById(R.id.textView3);

        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        // Get the userId (replace with your logic to fetch userId)
        String username = dbHelper.getUsername();

        if (username != null) {
            usernameTextView.setText(username);
        } else {
            usernameTextView.setText("User");
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnUserProfile=view.findViewById(R.id.BtnMyProfile);
        ImageButton btnUserWorkOut=view.findViewById(R.id.btnMyWorkout);
        ImageButton btnUserMood=view.findViewById(R.id.btnMyMood);
        ImageButton btnUserWellness=view.findViewById(R.id.btnMyWellness);
        ImageButton btnPrivacyPolicy=view.findViewById(R.id.btnPrivacyPolicy);
        ImageButton btnLanguage=view.findViewById(R.id.btnLanguage);

        View.OnClickListener UserProfile = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_userProfileFragment);
            }
        };
        btnUserProfile.setOnClickListener(UserProfile);

        View.OnClickListener UserWorkout = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_exerciseHomeFragment3);
            }
        };
        btnUserWorkOut.setOnClickListener(UserWorkout);

        View.OnClickListener UserMood = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_home3);
            }
        };
        btnUserMood.setOnClickListener(UserMood);

        View.OnClickListener UserWellness = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_dailyWellnessMain3);
            }
        };
        btnUserWellness.setOnClickListener(UserWellness);


    View.OnClickListener PrivacyPolicy = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_privacyPolicy);
        }
    };
        btnPrivacyPolicy.setOnClickListener(PrivacyPolicy);

    View.OnClickListener Language = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_languageFragment);
       }
    };
        btnLanguage.setOnClickListener(Language);
    }

}