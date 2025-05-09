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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madassignment4.R;

public class BreathingExercise extends Fragment {


    private TextView timerText, cycleText;
    private ImageButton playButton;
    private CountDownTimer countDownTimer, cycleCountDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis;  // Variable to hold the selected time
    private MediaPlayer mediaPlayer4, mediaPlayer8;
    private Dialog dialogDone, dialogConfirmation;
    private Button no_btn,yes_btn;
    private int cycleState = 0; // 0 for inhale, 1 for hold, 2 for exhale
    private TextView action;
    private ImageView action_img;

    public BreathingExercise() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_breathing_exercise, container, false);

        if (getArguments() != null) {
            timeLeftInMillis = getArguments().getLong("timeInMillis", 50000); // Default to 1 minute if no time is passed
        }

        timerText = view.findViewById(R.id.be_timer);
        cycleText = view.findViewById(R.id.be_timer2);
        playButton = view.findViewById(R.id.play_btn);
        action = view.findViewById(R.id.betv3);
        action_img = view.findViewById(R.id.beiv);

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

        mediaPlayer4 = MediaPlayer.create(getContext(), R.raw.four);
        mediaPlayer8 = MediaPlayer.create(getContext(), R.raw.eight);

        playButton.setOnClickListener(v -> {
            if (isTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
                startCycleTimer();
            }
        });

        done_btn.setOnClickListener(v -> {
            dialogDone.dismiss();
            Navigation.findNavController(view).navigate(R.id.mindfulnessExercise);
        });

        ImageButton backButton = view.findViewById(R.id.beback_btn);
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
                cycleCountDownTimer.cancel();
                dialogDone.show();
            }
        };

        countDownTimer.start();
        isTimerRunning = true;

        mediaPlayer4.start();

        // Update button to "Pause" icon
        playButton.setImageResource(R.drawable.stop_btn);  // Set to "Pause" icon
    }

    private void startCycleTimer() {
        int phaseDuration = getPhaseDuration(cycleState);

        cycleCountDownTimer = new CountDownTimer(phaseDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);

                if (cycleState == 0 && secondsRemaining == 5) {
                    cycleText.setText("4");
                    action.setText(getString(R.string.inhale));
                    action_img.setImageResource(R.drawable.inhale_stk);
                    mediaPlayer4.start();
                } else if (cycleState == 1 && secondsRemaining == 8) {
                    cycleText.setText("7");
                    action.setText(getString(R.string.hold));
                    action_img.setImageResource(R.drawable.hold_stk);
                } else if (cycleState == 2 && secondsRemaining == 9) {
                    cycleText.setText("8");
                    action.setText(getString(R.string.exhale));
                    action_img.setImageResource(R.drawable.exhale_stk);
                    mediaPlayer8.start();
                } else {
                    cycleText.setText(String.valueOf(secondsRemaining));

                }
            }

            @Override
            public void onFinish() {
                updateCycleState();
                startCycleTimer();
            }
        };

        cycleCountDownTimer.start();
    }

    private int getPhaseDuration(int state) {
        switch (state) {
            case 0:
                return 5000;
            case 1:
                return 8000;
            case 2:
                return 9000;
            default:
                return 0;
        }
    }

    private void updateCycleState() {
        switch (cycleState) {
            case 0:
                cycleState = 1;
                break;
            case 1:
                cycleState = 2;
                break;
            case 2:
                cycleState = 0;
                break;
        }
    }

    private void pauseTimer() {
        if (!isTimerRunning) return;  // If timer is not running, do nothing

        if (cycleCountDownTimer != null) {
            cycleCountDownTimer.cancel();  // Pause cycle countdown
        }

        if (countDownTimer != null) {
            countDownTimer.cancel();  // Pause main countdown
        }

        isTimerRunning = false;
        stopMusic();

        // Update button to "Start" icon
        playButton.setImageResource(R.drawable.play_btn);  // Set to "Start" icon
    }

    private void stopMusic() {
        if (mediaPlayer4 != null && mediaPlayer4.isPlaying()) {
            mediaPlayer4.pause();  // Pause the music
        }
        if (mediaPlayer8 != null && mediaPlayer8.isPlaying()) {
            mediaPlayer8.pause();  // Pause the music
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
        if (mediaPlayer4 != null) {
            mediaPlayer4.release();  // Release the media player resources when the fragment stops
        }
        if (mediaPlayer8 != null) {
            mediaPlayer8.release();  // Release the media player resources when the fragment stops
        }
    }
}