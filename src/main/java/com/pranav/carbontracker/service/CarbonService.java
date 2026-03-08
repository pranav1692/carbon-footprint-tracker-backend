package com.pranav.carbontracker.service;

import org.springframework.stereotype.Service;

@Service
public class CarbonService {

    public double calculateEmission(String category, double value){

        switch(category.toLowerCase()){

            case "car":
                return value * 0.21;

            case "bike":
                return value * 0.11;

            case "bus":
                return value * 0.05;

            case "electricity":
                return value * 0.82;

            default:
                throw new IllegalArgumentException("Invalid activity category");
        }
    }
}