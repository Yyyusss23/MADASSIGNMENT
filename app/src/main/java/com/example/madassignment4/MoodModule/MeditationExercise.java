package com.example.madassignment4.MoodModule;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.madassignment4.R;

public class MeditationExercise extends Fragment {


    private TextView timerText;
    private ImageButton playButton;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis;  // Variable to hold the selected time
    private MediaPlayer mediaPlayer;
    private Dialog dialogDone,dialogConfirmation;
    private Button no_btn, yes_btn;

    public MeditationExercise() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meditation_exercise, container, false);

        // Retrieve the time passed from the previous fragment
        if (getArguments() != null) {
            timeLeftInMillis = getArguments().getLong("timeInMillis", 60000); // Default to 1 minute if no time is passed
        }

        timerText = view.findViewById(R.id.me_timer);
        playButton = view.findViewById(R.id.play_btn);


        dialogDone = new Dialog(getContext());
        dialogDone.setContentView(R.layout.fragment_done_dialog_box);
        dialogDone.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogDone.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogDone.setCancelable(false);

        Button done_btn = dialogDone.findViewById(R.id.done_btn);

        dialogConfirmation = new Dialog(getContext());
        dialogConfirmation.setContentView(R.layout.fragment_confirmation_dialog_box);
        dialogConfirmation.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogConfirmation.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogConfirmation.setCancelable(false);

        no_btn = dialogConfirmation.findViewById(R.id.no_btn);
        yes_btn = dialogConfirmation.findViewById(R.id.yes_btn);

        updateTimerText(timeLeftInMillis);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.meditation_music);

        // Set an OnCompletionListener to restart the music once it finishes
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.seekTo(0);  // Reset the music to the beginning
            mp.start();    // Restart the music
        });

        playButton.setOnClickListener(v -> {
            if (isTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        done_btn.setOnClickListener(v -> {
            dialogDone.dismiss();
            Navigation.findNavController(view).navigate(R.id.mindfulnessExercise);
        });

        ImageButton backButton = view.findViewById(R.id.mmeback_btn);
        backButton.setOnClickListener(v -> {
            dialogConfirmation.show();
            pauseTimer();
            yes_btn.setOnClickListener(v1 -> {
                dialogConfirmation.dismiss();
                Navigation.findNavController(requireView()).navigate(R.id.mindfulnessExercise);
            });
            no_btn.setOnClickListener(v1 -> dialogConfirmation.dismiss());
        });


        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                dialogConfirmation.show();
                pauseTimer();

                yes_btn.setOnClickListener(v -> {
                    dialogConfirmation.dismiss();
                    Navigation.findNavController(requireView()).navigate(R.id.mindfulnessExercise);
                });

                no_btn.setOnClickListener(v -> dialogConfirmation.dismiss());
            }
        });
        return view;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText(timeLeftInMillis);
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                updateTimerText(0);
                playButton.setImageResource(R.drawable.play_btn);  // Set to "Start" icon
                stopMusic();
                dialogDone.show();
            }
        };

        countDownTimer.start();
        isTimerRunning = true;

        mediaPlayer.start();

        // Update button to "Pause" icon
        playButton.setImageResource(R.drawable.stop_btn);  // Set to "Pause" icon
    }

    private void pauseTimer() {
        if (!isTimerRunning) return;  // If timer is not running, do nothing

        countDownTimer.cancel();
        isTimerRunning = false;

        stopMusic();

        // Update button to "Start" icon
        playButton.setImageResource(R.drawable.play_btn);  // Set to "Start" icon
    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();  // Pause the music
        }
    }

    private void updateTimerText(long timeInMillis) {
        int minutes = (int) (timeInMillis / 1000) / 60;
        int seconds = (int) (timeInMillis / 1000) % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timerText.setText(timeFormatted);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();  // Release the media player resources when the fragment stops
        }
    }


}