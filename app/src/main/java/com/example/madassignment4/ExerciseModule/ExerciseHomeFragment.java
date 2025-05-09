package com.example.madassignment4.ExerciseModule;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;

public class ExerciseHomeFragment extends Fragment {

    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_home, container, false);

        // Calculate and update progress
        calculateAndSetProgress();

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Updated column names used in ExerciseHomeFragment
        Cursor cursor = null;
        StringBuilder descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("Your TODO List : \n");

        try {
            cursor = dbHelper.getLatestFitnessSettingAndGoals(db);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Updated column names
                    String exerciseType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GOAL_EXERCISE_TYPE));
                    String attributes = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GOAL_ATTRIBUTES));

                    // Format and append to the description
                    descriptionBuilder.append(exerciseType)
                            .append(" - ")
                            .append(attributes)
                            .append("\n");
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), "Error while querying database: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.close();

        TextView tvPGDescription = view.findViewById(R.id.TVPGDescription);
        tvPGDescription.setText(descriptionBuilder.toString());

        // Get references to buttons and progress bar
        ImageButton btnProgressGoal = view.findViewById(R.id.BtnProgressGoal);
        ImageButton btnLog = view.findViewById(R.id.BtnLog);
        ImageButton btnHistory = view.findViewById(R.id.BtnHistory);
        progressBar = view.findViewById(R.id.PBProgressGoal); // Reference to the progress bar

        // Set up button click listeners
        View.OnClickListener ProgressGoal = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_exerciseHomeFragment3_to_progressGoalFragment2);
            }
        };
        btnProgressGoal.setOnClickListener(ProgressGoal);

        View.OnClickListener Log = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_exerciseHomeFragment3_to_logFragment2);
            }
        };
        btnLog.setOnClickListener(Log);

        View.OnClickListener History = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_exerciseHomeFragment3_to_historyFragment2);
            }
        };
        btnHistory.setOnClickListener(History);

        ImageButton backButton = view.findViewById(R.id.imageButton);
        backButton.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_exerciseHomeFragment3_to_trackerMain3);
        });

        return view;
    }

    private void calculateAndSetProgress() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        new Thread(() -> {
            try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
                int latestFitnessID = -1;
                try (Cursor latestFitnessCursor = db.rawQuery(
                        "SELECT MAX(" + DatabaseHelper.COLUMN_FITNESS_ID + ") AS latestFitnessID FROM " + DatabaseHelper.TABLE_FITNESS_SETTING,
                        null)) {
                    if (latestFitnessCursor != null && latestFitnessCursor.moveToFirst()) {
                        latestFitnessID = latestFitnessCursor.isNull(0) ? -1 : latestFitnessCursor.getInt(0);
                    }
                }

                if (latestFitnessID == -1) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "No fitness setting found.", Toast.LENGTH_SHORT).show()
                    );
                    return;
                }

                int totalLogAttributes = 0;
                try (Cursor logCursor = db.rawQuery(
                        "SELECT IFNULL(SUM(CAST(REPLACE(" + DatabaseHelper.COLUMN_LOG_ATTRIBUTES +
                                ", SUBSTR(" + DatabaseHelper.COLUMN_LOG_ATTRIBUTES + ", INSTR(" + DatabaseHelper.COLUMN_LOG_ATTRIBUTES + ", ' '), LENGTH(" + DatabaseHelper.COLUMN_LOG_ATTRIBUTES + ")), '') AS INTEGER)), 0) AS totalAttributes " +
                                "FROM " + DatabaseHelper.TABLE_EXERCISE_LOG +
                                " WHERE " + DatabaseHelper.COLUMN_LOG_FITNESS_ID + " = ?",
                        new String[]{String.valueOf(latestFitnessID)})) {
                    if (logCursor != null && logCursor.moveToFirst()) {
                        totalLogAttributes = logCursor.getInt(0);
                    }
                }

                int totalGoalAttributes = 0;
                try (Cursor goalCursor = db.rawQuery(
                        "SELECT IFNULL(SUM(CAST(REPLACE(" + DatabaseHelper.COLUMN_GOAL_ATTRIBUTES +
                                ", SUBSTR(" + DatabaseHelper.COLUMN_GOAL_ATTRIBUTES + ", INSTR(" + DatabaseHelper.COLUMN_GOAL_ATTRIBUTES + ", ' '), LENGTH(" + DatabaseHelper.COLUMN_GOAL_ATTRIBUTES + ")), '') AS INTEGER)), 0) AS totalAttributes " +
                                "FROM " + DatabaseHelper.TABLE_GOAL_SETTING +
                                " WHERE " + DatabaseHelper.COLUMN_GOAL_FITNESS_ID + " = ?",
                        new String[]{String.valueOf(latestFitnessID)})) {
                    if (goalCursor != null && goalCursor.moveToFirst()) {
                        totalGoalAttributes = goalCursor.getInt(0);
                    }
                }

                int progress = (totalGoalAttributes == 0) ? 0 : (int) (((float) totalLogAttributes / totalGoalAttributes) * 100);
                getActivity().runOnUiThread(() -> progressBar.setProgress(progress));

            } catch (Exception e) {
                Log.e("ExerciseHomeFragment", "Error calculating progress", e);
                getActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error calculating progress: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}