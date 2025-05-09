package com.example.madassignment4.DailyWellnessModule;

public class HydrationIntakeModel {
    private String intakeTimestamp;
    private int quantityOfWater;

    // Constructor
    public HydrationIntakeModel(String intakeTimestamp, int quantityOfWater) {
        this.intakeTimestamp = intakeTimestamp;
        this.quantityOfWater = quantityOfWater;
    }

    // Getters
    public String getIntakeTimestamp() {
        return intakeTimestamp;
    }

    public int getQuantityOfWater() {
        return quantityOfWater;
    }


    public void setQuantityOfWater(int quantityOfWater) {
        this.quantityOfWater = quantityOfWater;
    }
}


