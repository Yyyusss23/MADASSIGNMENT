package com.example.madassignment4.MoodModule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class SelectMood extends Fragment {

    private DatabaseHelper databaseHelper;
    private String selectedDate;

    public SelectMood() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hide BottomNavigationView
        BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
        bottomNav.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_mood, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        if (getArguments() != null) {
            selectedDate = getArguments().getString("selectedDate");
        }

        view.findViewById(R.id.excited_btn).setOnClickListener(v -> saveMood("Excited"));
        view.findViewById(R.id.happy_btn).setOnClickListener(v -> saveMood("Happy"));
        view.findViewById(R.id.sad_btn).setOnClickListener(v -> saveMood("Sad"));
        view.findViewById(R.id.neutral_btn).setOnClickListener(v -> saveMood("Neutral"));
        view.findViewById(R.id.anxious_btn).setOnClickListener(v -> saveMood("Anxious"));
        view.findViewById(R.id.angry_btn).setOnClickListener(v -> saveMood("Angry"));

        ImageButton backButton = view.findViewById(R.id.fback_btn);
        backButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("selectedDate", selectedDate);
            Navigation.findNavController(requireView()).navigate(R.id.action_selectMood_to_home3, bundle);
        });

        return view;
    }

    private void saveMood(String mood) {
        databaseHelper.saveMood(databaseHelper.getUserIdByMostRecentLogin(),selectedDate, mood);
        Toast.makeText(getContext(), "Mood saved!", Toast.LENGTH_SHORT).show();

        // Pass selectedDate back to HomeFragment
        Bundle bundle = new Bundle();
        bundle.putString("selectedDate", selectedDate);

        // Navigate back to HomeFragment with the bundle
        Navigation.findNavController(requireView()).navigate(R.id.action_selectMood_to_home3, bundle);
    }
}