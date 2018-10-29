package com.example.liam.medschedule;

//medicine class for storing to and calling from Sqlite database
public class medicines {
    int id;
    String medicine_name;
    int dose;
    String unit;
    String medicine_desc;
    String date;

    public medicines(int id, String medicine_name, int dose, String unit, String medicine_desc, String date) {
        this.id = id;
        this.medicine_name = medicine_name;
        this.dose = dose;
        this.unit = unit;
        this.medicine_desc = medicine_desc;
        this.date = date;
    }

    public medicines(String medicine_name, int dose, String unit,
                     String medicine_desc, String date) {
        this.medicine_name = medicine_name;
        this.dose = dose;
        this.unit = unit;
        this.medicine_desc = medicine_desc;
        this.date = date;
    }

    public medicines() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMedicine_desc() {
        return medicine_desc;
    }

    public void setMedicine_desc(String medicine_desc) {
        this.medicine_desc = medicine_desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
