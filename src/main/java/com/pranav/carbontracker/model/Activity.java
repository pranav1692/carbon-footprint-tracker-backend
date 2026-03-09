package com.pranav.carbontracker.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String category;

    private double value;

    private double carbonEmission;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private LocalDate  date;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public double getValue() { return value; }

    public void setValue(double value) { this.value = value; }

    public double getCarbonEmission() { return carbonEmission; }

    public void setCarbonEmission(double carbonEmission) { this.carbonEmission = carbonEmission; }
}