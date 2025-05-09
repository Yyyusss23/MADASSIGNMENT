package com.example.madassignment4.DailyWellnessModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madassignment4.R;


public class bmiCalculator extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bmi_calculator, container, false);

        // Initialize the UI elements
        EditText heightInput = view.findViewById(R.id.ETHeight);
        EditText weightInput = view.findViewById(R.id.ETWeight);
        Button BtnCalculate = view.findViewById(R.id.BtnCalculate);
        TextView resultBMI = view.findViewById(R.id.ResultBMI);
        TextView yourBMI = view.findViewById(R.id.YourBmi);
        TextView resultBMICategory = view.findViewById(R.id.ResultBMICategory);
        TextView BMICategories = view.findViewById(R.id.BMICategories);
        ImageView BMICategoriesTable = view.findViewById(R.id.BMICategoriesTable);
        ImageButton BtnBack = view.findViewById(R.id.back_btn);
        TextView underWeight = view.findViewById(R.id.textView19);
        TextView normal = view.findViewById(R.id.textView21);
        TextView overweight = view.findViewById(R.id.textView23);
        TextView obese = view.findViewById(R.id.textView22);

        // Set up button click listener
        BtnCalculate.setOnClickListener(v -> {
            String StrHeight = heightInput.getText().toString().trim();
            String StrWeight = weightInput.getText().toString().trim();

            if (!StrHeight.isEmpty() && !StrWeight.isEmpty()) {
                try {
                    double height = Double.parseDouble(StrHeight);
                    double weight = Double.parseDouble(StrWeight);

                    if (height > 0 && weight > 0) {
                        double BMI = weight / (height * height);

                        resultBMI.setText(String.format("%.2f", BMI));
                        resultBMI.setVisibility(View.VISIBLE);

                        String category;
                        if (BMI <= 18.4) {
                            category = getString(R.string.bmi_underweight);
                        } else if (BMI >= 18.5 && BMI <= 24.9) {
                            category = getString(R.string.bmi_normal);
                        } else if (BMI >= 25.0 && BMI <= 29.9) {
                            category = getString(R.string.bmi_overweight);
                        } else {
                            category = getString(R.string.bmi_obese);
                        }

                        resultBMICategory.setText(category);
                        yourBMI.setVisibility(View.VISIBLE);
                        resultBMICategory.setVisibility(View.VISIBLE);
                        BMICategories.setVisibility(View.VISIBLE);
                        BMICategoriesTable.setVisibility(View.VISIBLE);
                        underWeight.setVisibility(View.VISIBLE);
                        normal.setVisibility(View.VISIBLE);
                        overweight.setVisibility(View.VISIBLE);
                        obese.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(requireContext(), "Please enter valid positive values for height and weight.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Please enter numeric values for height and weight.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Please fill in both height and weight fields.", Toast.LENGTH_SHORT).show();
            }
        });
        View.OnClickListener Back = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_bmiCalculator_to_dailyWellnessMain3);
            }
        };
        BtnBack.setOnClickListener(Back);
        return view;
    }
}