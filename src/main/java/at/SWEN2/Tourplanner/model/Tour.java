package at.SWEN2.Tourplanner.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Tour {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "from_location")
    private String fromLocation;

    @Column(name = "to_location")
    private String toLocation;

    @Column(name = "transport_type")
    private String transportType; // Changed to String

    @Column(name = "distance")
    private Double distance;

    @Column(name = "estimated_time")
    private Double estimatedTime;

    @Column(name = "map_image_url")
    private String mapImageUrl;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TourLog> tourLogs;

    public void addTourLog(TourLog tourLog) {
        tourLogs.add(tourLog);
        tourLog.setTour(this);
    }

    public void removeTourLog(TourLog tourLog) {
        tourLogs.remove(tourLog);
        tourLog.setTour(null);
    }

    public String getMapImageUrl() {
        return mapImageUrl;
    }

    public void setMapImageUrl(String mapImageUrl) {
        this.mapImageUrl = mapImageUrl;
    }

    public String getFromLocation() {
        return fromLocation;
    }
    public String getToLocation() {
        return toLocation;
    }
}
