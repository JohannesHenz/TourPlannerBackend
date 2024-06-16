package at.SWEN2.Tourplanner.model;

import lombok.Data;

@Data
public class RouteInfo {
    private double distance;
    private double duration;
    private String geometry;

    // Getters and Setters
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }



}
