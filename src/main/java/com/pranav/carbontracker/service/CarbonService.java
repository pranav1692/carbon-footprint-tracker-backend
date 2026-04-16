package com.pranav.carbontracker.service;

import org.springframework.stereotype.Service;

@Service
public class CarbonService {

    public double calculateEmission(String vehicleType,
                                    String fuelType,
                                    double distance,
                                    int passengers) {

        double emission = 0;

        switch (vehicleType.toLowerCase()) {

            case "bike":
                emission = (distance / 40) * 2.31;
                break;

            case "car":

                if(fuelType == null) {
                    fuelType = "petrol";
                }

                if(fuelType.equalsIgnoreCase("diesel")){
                    emission = (distance / 15) * 2.68;
                }else{
                    emission = (distance / 15) * 2.31;
                }

                break;

            case "bus":
                emission = (distance / 5) * 2.68;
                break;

            case "train":
                emission = distance * 0.04;
                break;

            case "flight":
                emission = distance * 0.25;
                break;

            default:
                emission = distance * 0.2;
        }

        if(passengers <= 0){
            passengers = 1;
        }

        double result = emission / passengers;

        return Math.round(result * 100.0) / 100.0;
    }
}