package at.SWEN2.Tourplanner.dto;

import at.SWEN2.Tourplanner.model.TransportType;

import java.util.List;

public class RouteRequest {
    private String from;
    private String to;
    private String transportType;

    public RouteRequest() {}

    public RouteRequest(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }
}
