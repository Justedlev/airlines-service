package com.justedlev.service.airlines.service;

import com.justedlev.service.airlines.service.interfaces.IAirlines;

public abstract class AbstractAirlinesService implements IAirlines {

    protected double calculatePrice(double originalPrice, long countMonthsUse) {
        return originalPrice * (1 - countMonthsUse * 0.02);
    }

    protected double calculateDistance(double latA, double lonA, double latB, double lonB) {
        double dLat = Math.toRadians(latB - latA);
        double dLon = Math.toRadians(lonB - lonA);
        double a = Math.pow(
                Math.sin(dLat / 2), 2) +
                Math.pow(
                        Math.sin(dLon / 2), 2) *
                        Math.cos(Math.toRadians(latA)) * Math.cos(Math.toRadians(latB)
                );
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }

}
