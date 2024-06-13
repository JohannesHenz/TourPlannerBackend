package at.SWEN2.Tourplanner.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "from_location")
    private String fromLocation;

    @Column(name = "to_location")
    private String toLocation;

    @Column(name = "transport_type")
    private String transportType;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "estimated_time")
    private Double estimatedTime;
    //private String routeInfoImagePath;

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
}
