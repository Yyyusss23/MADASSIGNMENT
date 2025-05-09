package com.example.madassignment4.DailyWellnessModule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class StepTracking extends Fragment implements SensorEventListener {

    private TextView totalStepsView, totalKMView, totalCaloriesView, goalStepsView;
    private CircularProgressBar circularProgressBar;
    private Button btnStart, btnStop,btnHistory;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int stepCount = 0;
    private float distanceWalked = 0;
    private float caloriesBurned = 0;
    private int stepGoal = 0;
    private boolean isTracking = false;
    private boolean isGoalAchieved = false;
    private long lastStepTime = 0;
    private static final long DEBOUNCE_TIME = 300;
    private DatabaseHelper dbHelper;
    private String currentDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_tracking, container, false);

        // Initialize UI elements
        totalStepsView = view.findViewById(R.id.TotalStep);
        totalKMView = view.findViewById(R.id.totalKM);
        totalCaloriesView = view.findViewById(R.id.TotalCaloriesBurned);
        circularProgressBar = view.findViewById(R.id.CircularProgressBar);
        goalStepsView = view.findViewById(R.id.GoalSteps);
        btnStart = view.findViewById(R.id.BtnStart);
        btnStop = view.findViewById(R.id.BtnStop);
        btnHistory=view.findViewById(R.id.BtnStepHistory);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(requireContext());
        String userId = dbHelper.getUserIdByMostRecentLogin();
        currentDate = getCurrentDate();

        // Prompt user for step goal
        showStepGoalDialog();

        // Set up button listeners
        btnStart.setOnClickListener(v -> startTracking());
        btnStop.setOnClickListener(v -> stopTracking());

        View.OnClickListener StepHistory = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_stepTracking_to_stepHistory2);
            }
        };
        btnHistory.setOnClickListener(StepHistory);

        // Back Button setup
        ImageButton btnBack = view.findViewById(R.id.back_btn);
        View.OnClickListener Back = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_stepTracking_to_dailyWellnessMain3);
            }
        };
        btnBack.setOnClickListener(Back);


        return view;
    }

    private void showStepGoalDialog() {
        String userId = dbHelper.getUserIdByMostRecentLogin();
        int existingGoal = dbHelper.getStepGoal(userId, currentDate);
        if (existingGoal != -1) {
            // If a goal exists, show a message and prevent the user from setting a new goal
            goalStepsView.setText(String.valueOf(existingGoal));
            stepGoal=existingGoal;
            Toast.makeText(requireContext(), "Step goal already set for today.", Toast.LENGTH_SHORT).show();
            return;  // Don't allow the user to set a new goal
        }

        // If no goal exists, show the dialog for input
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_steps_goal);
        dialog.setCancelable(false);
        EditText goalInputField = dialog.findViewById(R.id.ETStepsInput);
        Button btnSetGoal = dialog.findViewById(R.id.BtnSetGoal);

        btnSetGoal.setOnClickListener(v -> {
            String inputGoal = goalInputField.getText().toString();
            if (!inputGoal.isEmpty()) {
                try {
                    int newGoal = Integer.parseInt(inputGoal);
                    if (newGoal > 0) {
                        stepGoal = newGoal;
                        goalStepsView.setText(String.valueOf(stepGoal));
                        Toast.makeText(requireContext(), "Step goal set to " + stepGoal, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        // Save the step goal in the database
                        dbHelper.saveStepGoal(userId, currentDate, stepGoal);
                    } else {
                        Toast.makeText(requireContext(), "Please enter a positive number.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Invalid input. Please enter a number.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Goal cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


    private void startTracking() {
        String userId = dbHelper.getUserIdByMostRecentLogin();
        if (!isTracking) {
            // Retrieve the total step count, distance walked, and calories burned for the day from the database
            stepCount = dbHelper.getTotalStepsForDay(userId, currentDate);
            distanceWalked = dbHelper.getDistanceWalkedForDay(userId, currentDate);
            caloriesBurned = dbHelper.getCaloriesBurnedForDay(userId, currentDate);

            sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
                isTracking = true;
                Toast.makeText(requireContext(), "Step tracking started", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Sensor not available", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void stopTracking() {
        String userId = dbHelper.getUserIdByMostRecentLogin();
        if (isTracking) {
            sensorManager.unregisterListener(this);
            isTracking = false;
            Toast.makeText(requireContext(), "Step tracking stopped", Toast.LENGTH_SHORT).show();

            // Save or update the step data in the database
            dbHelper.saveOrUpdateStepTrackingData(userId, stepGoal, stepCount, distanceWalked, caloriesBurned, currentDate);

            // Reset for the next session
            stepCount = 0;
            distanceWalked = 0;
            caloriesBurned = 0;
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && isTracking) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            long currentTime = System.currentTimeMillis();

            // Check if the sensor values indicate movement
            if ((Math.abs(x) > 5 || Math.abs(y) > 5 || Math.abs(z) > 5) && (currentTime - lastStepTime > DEBOUNCE_TIME)) {
                stepCount++;
                distanceWalked += 0.762f; // Average step length in meters
                caloriesBurned += 0.04f; // Calories burned per step
                lastStepTime = currentTime;

                updateProgressBar(stepCount);

                // Check if the goal has been achieved
                if (stepCount >= stepGoal && !isGoalAchieved) {
                    isGoalAchieved = true;
                    showGoalAchievedDialog();
                }
            }
        }
    }

    private void updateProgressBar(int stepCount) {
        float progress = ((float) stepCount / stepGoal) * 100;
        circularProgressBar.setProgress(progress);
        totalStepsView.setText(String.valueOf(stepCount));
        totalKMView.setText(String.format("%.2f", distanceWalked / 1000));
        totalCaloriesView.setText(String.format("%.2f", caloriesBurned));
    }

    private void showGoalAchievedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_achieve_goal, null);
        builder.setView(dialogView);
        Button btnCloseDialog = dialogView.findViewById(R.id.btnCloseDialog);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        btnCloseDialog.setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onPause() {
        super.onPause();
        if (isTracking) {
            sensorManager.unregisterListener(this);
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
