package com.example.madassignment4.ExerciseModule;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;

import java.util.HashMap;

public class HistorySummaryFragment extends Fragment {

    private DatabaseHelper databaseHelper;

    private TextView tvSummaryRun, tvSummaryWalk, tvSummaryCycle, tvSummarySwim, tvSummaryOtherCardio;
    private TextView tvSummaryWorkout, tvSummaryWeightlift, tvSummaryOtherWeightTraining;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_summary, container, false);

        // Initialize TextViews
        tvSummaryRun = view.findViewById(R.id.TVSummaryRun);
        tvSummaryWalk = view.findViewById(R.id.TVSummaryWalk);
        tvSummaryCycle = view.findViewById(R.id.TVSummaryCycle);
        tvSummarySwim = view.findViewById(R.id.TVSummarySwim);
        tvSummaryOtherCardio = view.findViewById(R.id.TVSummaryOtherCardio);
        tvSummaryWorkout = view.findViewById(R.id.TVSummaryWorkout);
        tvSummaryWeightlift = view.findViewById(R.id.TVSummaryWeightlift);
        tvSummaryOtherWeightTraining = view.findViewById(R.id.TVSummaryOtherWeightTraining);

        // Back button functionality
        Button btnBack = view.findViewById(R.id.BtnHistorySummaryBack);
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // Load summary data from database
        loadSummaryData();

        return view;
    }

    private void loadSummaryData() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT " + DatabaseHelper.COLUMN_LOG_EXERCISE_TYPE + ", SUM(CAST(" + DatabaseHelper.COLUMN_LOG_ATTRIBUTES + " AS INTEGER)) AS Total FROM "
                    + DatabaseHelper.TABLE_EXERCISE_LOG + " GROUP BY " + DatabaseHelper.COLUMN_LOG_EXERCISE_TYPE + ";";
            cursor = db.rawQuery(query, null);

            // Map TextView IDs to exercise types
            HashMap<String, TextView> exerciseMapping = new HashMap<>();
            exerciseMapping.put("Run", tvSummaryRun);
            exerciseMapping.put("Walk", tvSummaryWalk);
            exerciseMapping.put("Cycle", tvSummaryCycle);
            exerciseMapping.put("Swim", tvSummarySwim);
            exerciseMapping.put("Others", tvSummaryOtherCardio);
            exerciseMapping.put("Workout", tvSummaryWorkout);
            exerciseMapping.put("Weight-Lift", tvSummaryWeightlift);
            exerciseMapping.put("WeightTrainingOthers", tvSummaryOtherWeightTraining);

            if (cursor.moveToFirst()) {
                do {
                    String exerciseType = cursor.getString(0);
                    int total = cursor.getInt(1);

                    // Update corresponding TextView
                    if (exerciseMapping.containsKey(exerciseType)) {
                        TextView targetView = exerciseMapping.get(exerciseType);
                        if (targetView != null) {
                            targetView.setText(String.valueOf(total) + (exerciseType.equals("Workout") || exerciseType.contains("Weight") ? " min" : " km"));
                        }
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error loading summary: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }
}
