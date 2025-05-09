package com.example.madassignment4.DailyWellnessModule;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class HydrationHistory extends Fragment {

    private BarChart barChart;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hydration_history, container, false);

        barChart = view.findViewById(R.id.barChart);
        dbHelper = new DatabaseHelper(getActivity());

        // Get the date range for the last 7 days
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        String endDate = sdf.format(calendar.getTime());  // Today's date
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        String startDate = sdf.format(calendar.getTime());  // Date 6 days ago

        // Retrieve daily intake data as a Map<Date, TotalIntake>
        String userId = dbHelper.getUserIdByMostRecentLogin();
        Map<String, Integer> dailyIntake = dbHelper.getTotalWaterIntakeByDay(userId, startDate, endDate);

        // Prepare BarChart entries and x-axis labels
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> xAxisLabels = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Integer> entry : dailyIntake.entrySet()) {
            xAxisLabels.add(entry.getKey());  // Add the date to x-axis labels
            entries.add(new BarEntry(index++, entry.getValue()));  // Add bar data
        }

        // Set up the BarDataSet
        BarDataSet dataSet = new BarDataSet(entries, "Water Intake");
        dataSet.setColor(Color.parseColor("#ff914d"));
        dataSet.setValueTextSize(14f);

        // Disable values on top of the bars
        dataSet.setDrawValues(false);

        // Create BarData and set it to the chart
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.7f);
        barChart.setData(barData);

        // Customize Y-Axis values
        YAxis leftAxis = barChart.getAxisLeft();  // Get the left Y-axis
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);  // Put Y-axis labels outside

        // X-axis customization
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(315f);  // Rotate labels for readability
        xAxis.setGranularity(1f);  // Ensure each bar gets a label
        xAxis.setDrawGridLines(false);

        // Additional chart customization
        barChart.getDescription().setEnabled(false);
        barChart.setTouchEnabled(true);
        barChart.animateY(1000);  // Animate bars
        barChart.invalidate();  // Refresh the chart

        // Back Button setup
        ImageButton btnBack = view.findViewById(R.id.back_btn);
        View.OnClickListener Back = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_hydrationHistory2_to_hydrationTracking);
            }
        };
        btnBack.setOnClickListener(Back);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
