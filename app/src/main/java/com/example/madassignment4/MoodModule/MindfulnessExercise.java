package com.example.madassignment4.MoodModule;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.madassignment4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MindfulnessExercise extends Fragment {

    private Button B1_btn, B3_btn, B5_btn, M15_btn, M25_btn, M5_btn;
    private Dialog dialogB, dialogM;
    private ImageButton exit_btn1,exit_btn2,back_btn;
    private Button start_btn1,start_btn2;
    private long selectedTime = 0;

    public MindfulnessExercise() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.action_mindfulnessExercise_to_home3);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hide BottomNavigationView
        BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
        bottomNav.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mindfulness_exercise, container, false);

        B1_btn = view.findViewById(R.id.B1_btn);
        B3_btn = view.findViewById(R.id.B3_btn);
        B5_btn = view.findViewById(R.id.B5_btn);
        M5_btn = view.findViewById(R.id.M5_btn);
        M15_btn = view.findViewById(R.id.M15_btn);
        M25_btn = view.findViewById(R.id.M25_btn);
        back_btn = view.findViewById(R.id.back_btn);

        dialogB = new Dialog(getContext());
        dialogB.setContentView(R.layout.fragment_exerciseone_dialog_box);
        dialogB.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogB.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogB.setCancelable(false);

        dialogM = new Dialog(getContext());
        dialogM.setContentView(R.layout.fragment_exercisetwo_dialog_box);
        dialogM.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogM.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogM.setCancelable(false);

        // Initialize the buttons inside the dialog
        exit_btn1 = dialogB.findViewById(R.id.exit_btn);
        start_btn1 = dialogB.findViewById(R.id.start_btn);
        exit_btn2 = dialogM.findViewById(R.id.exit_btn);
        start_btn2 = dialogM.findViewById(R.id.start_btn);

        B1_btn.setOnClickListener(v -> {
            showDialogB();
            selectedTime = 1 * 60 * 1000;
        });
        B3_btn.setOnClickListener(v -> {
            showDialogB();
            selectedTime = 3 * 60 * 1000;
        });
        B5_btn.setOnClickListener(v -> {
            showDialogB();
            selectedTime = 5 * 60 * 1000;
        });
        M5_btn.setOnClickListener(v -> {
            showDialogM();
            selectedTime = 5 * 60 * 1000;
        });  // 5 minutes
        M15_btn.setOnClickListener(v -> {
            showDialogM();
            selectedTime = 15 * 60 * 1000;
        });
        M25_btn.setOnClickListener(v -> {
            showDialogM();
            selectedTime = 25 * 60 * 1000;
        });

        // Set click listener for the exit button inside the dialog
        exit_btn1.setOnClickListener(v -> dialogB.dismiss());  // Dismiss the dialog
        exit_btn2.setOnClickListener(v -> dialogM.dismiss());  // Dismiss the dialog


        start_btn1.setOnClickListener(v -> {
            dialogB.dismiss();  // Close the dialog
            // Pass the selected time to the MeditationExercise fragment
            Bundle bundle = new Bundle();
            bundle.putLong("timeInMillis", selectedTime);
            Navigation.findNavController(view).navigate(R.id.breathingExercise2, bundle);
        });

        start_btn2.setOnClickListener(v -> {
            dialogM.dismiss();  // Close the dialog
            // Pass the selected time to the MeditationExercise fragment
            Bundle bundle = new Bundle();
            bundle.putLong("timeInMillis", selectedTime);
            Navigation.findNavController(view).navigate(R.id.meditationExercise2, bundle);
        });

        back_btn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_mindfulnessExercise_to_home3);
        });

        return view;
    }

    private void showDialogM() {
        dialogM.show();  // Show the dialog
    }

    private void showDialogB() {
        dialogB.show();  // Show the dialog
    }

}