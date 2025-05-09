package com.example.madassignment4.DailyWellnessModule;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class StepHistory extends Fragment {

    private BarChart barChart;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_history, container, false);


        barChart = view.findViewById(R.id.barChart);
        dbHelper = new DatabaseHelper(getActivity());

        // Get the date range for the last 7 days
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        String endDate = sdf.format(calendar.getTime());  // Today's date
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        String startDate = sdf.format(calendar.getTime());  // Date 6 days ago

        // Retrieve the step history data
        String userId = dbHelper.getUserIdByMostRecentLogin();
        ArrayList<String> dates = getDateRange(startDate, endDate);

        // Prepare BarChart entries and x-axis labels
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> xAxisLabels = new ArrayList<>();
        int index = 0;

        // Loop through the dates in the range and get total steps for each day
        for (String date : dates) {
            int steps = dbHelper.getTotalStepsForDay(userId, date);  // Get the steps for each date

            xAxisLabels.add(date);  // Add the date to x-axis labels
            entries.add(new BarEntry(index++, steps));  // Add bar data for steps
        }

        // Set up the BarDataSet
        BarDataSet dataSet = new BarDataSet(entries, "Steps Count");
        dataSet.setColor(Color.parseColor("#ff914d"));
        dataSet.setValueTextSize(14f);

        // Disable values on top of the bars
        dataSet.setDrawValues(false);

        // Create BarData and set it to the chart
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.7f);  // Adjust bar width for better appearance
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
        barChart.getDescription().setEnabled(false);  // Remove description
        barChart.setTouchEnabled(true);
        barChart.animateY(1000);  // Animate bars
        barChart.invalidate();  // Refresh the chart

        // Back Button setup
        ImageButton btnBack = view.findViewById(R.id.back_btn);
        View.OnClickListener Back = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_stepHistory2_to_stepTracking);
            }
        };
        btnBack.setOnClickListener(Back);

        return view;
    }

    // Helper method to get the date range for the last 7 days
    private ArrayList<String> getDateRange(String startDate, String endDate) {
        ArrayList<String> dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            // Add dates to the list in the range
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            while (!calendar.getTime().after(end)) {
                dates.add(sdf.format(calendar.getTime()));
                calendar.add(Calendar.DAY_OF_YEAR, 1);  // Increment by 1 day
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dates;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
