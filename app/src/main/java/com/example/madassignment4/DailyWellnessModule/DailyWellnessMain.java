package com.example.madassignment4.DailyWellnessModule;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.madassignment4.R;

public class DailyWellnessMain extends Fragment {


    public DailyWellnessMain() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daily_wellness_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find buttons in the layout
        Button BtnHydrationTracking = view.findViewById(R.id.BtnHydrationTracker);
        Button BtnStepTracking = view.findViewById(R.id.BtnStepTracking);
        Button BtnBMICalculator = view.findViewById(R.id.BtnBMICalculator);
        ImageButton BtnBack = view.findViewById(R.id.back_btn);
        View.OnClickListener Back = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_dailyWellnessMain3_to_trackerMain3);
            }
        };
        BtnBack.setOnClickListener(Back);


        View.OnClickListener HydrationTracking = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_dailyWellnessMain3_to_hydrationTracking);
            }
        };
        BtnHydrationTracking.setOnClickListener(HydrationTracking);

        View.OnClickListener StepTracking = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_dailyWellnessMain3_to_stepTracking);
            }
        };
        BtnStepTracking.setOnClickListener(StepTracking);

        View.OnClickListener BMICalculator = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_dailyWellnessMain3_to_bmiCalculator);
            }
        };
        BtnBMICalculator.setOnClickListener(BMICalculator);

    }
}