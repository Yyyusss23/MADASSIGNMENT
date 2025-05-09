package com.example.madassignment4;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TrackerMain extends Fragment {

    public TrackerMain(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracker_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button BtnMoodTracker = view.findViewById(R.id.BtnMoodTracker);
        View.OnClickListener MoodTracker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_trackerMain3_to_home3);
            }
        };
        BtnMoodTracker.setOnClickListener(MoodTracker);

        Button BtnWorkoutTracker = view.findViewById(R.id.BtnWorkoutTracker);
        View.OnClickListener WorkoutTracker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_trackerMain3_to_exerciseHomeFragment3);
            }
        };
        BtnWorkoutTracker.setOnClickListener(WorkoutTracker);

        Button BtnWellnessTracker = view.findViewById(R.id.BtnWellnessTracker);
        View.OnClickListener WellnessTracker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_trackerMain3_to_dailyWellnessMain3);
            }
        };
        BtnWellnessTracker.setOnClickListener(WellnessTracker);
    }
}
