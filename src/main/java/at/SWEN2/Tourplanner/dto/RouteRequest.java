package at.SWEN2.Tourplanner.dto;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RouteRequest {

    private List<List<Double>> coordinates;

    public RouteRequest(Coordinates from, Coordinates to) {
        this.coordinates = Arrays.asList(
                Arrays.asList(from.getLongitude(), from.getLatitude()),
                Arrays.asList(to.getLongitude(), to.getLatitude())
        );
    }

    @JsonIgnore
    public List<Double> getFrom() {
        return coordinates.get(0);
    }

    @JsonIgnore
    public List<Double> getTo() {
        return coordinates.get(1);
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return "RouteRequest{" +
                "coordinates=" + coordinates +
                '}';
    }
}