package com.example.madassignment4.ExerciseModule;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerViewHistory;
    private HistoryAdapter historyAdapter;
    private ArrayList<HashMap<String, String>> historyList;
    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
        historyList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        Button btnSummary = view.findViewById(R.id.BtnHistorySummary);
        View.OnClickListener Summary = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_historyFragment2_to_historySummaryFragment2);
            }
        };
        btnSummary.setOnClickListener(Summary);

        // Initialize RecyclerView
        recyclerViewHistory = view.findViewById(R.id.recyclerViewHistory);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new HistoryAdapter(historyList);
        recyclerViewHistory.setAdapter(historyAdapter);

        // Load data from database
        loadHistoryFromDatabase();

        ImageButton backButton = view.findViewById(R.id.imageButton3);
        backButton.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_historyFragment2_to_exerciseHomeFragment3);
        });

        return view;
    }

    private void loadHistoryFromDatabase() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Clear the history list to avoid duplication
            historyList.clear();

            String query = "SELECT " + DatabaseHelper.COLUMN_LOG_DATE + ", " + DatabaseHelper.COLUMN_LOG_EXERCISE_TYPE + ", " + DatabaseHelper.COLUMN_LOG_ATTRIBUTES + " FROM " + DatabaseHelper.TABLE_EXERCISE_LOG + " ORDER BY " + DatabaseHelper.COLUMN_LOG_DATE + " DESC;";
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> exerciseLog = new HashMap<>();
                    exerciseLog.put("Date", cursor.getString(0));
                    exerciseLog.put("ExerciseType", cursor.getString(1));
                    exerciseLog.put("Attributes", cursor.getString(2));

                    historyList.add(exerciseLog);
                } while (cursor.moveToNext());
            }

            historyAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            Toast.makeText(getContext(), "Error loading history: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
