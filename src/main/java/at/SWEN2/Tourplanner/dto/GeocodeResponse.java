package at.SWEN2.Tourplanner.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GeocodeResponse {
    private List<Feature> features;

    @Setter
    @Getter
    public static class Feature {
        private Geometry geometry;

    }

    @Setter
    @Getter
    public static class Geometry {
        private List<Double> coordinates;

    }
}
