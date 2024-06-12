package at.SWEN2.Tourplanner.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String fromLocation;
    private String toLocation;
    private String transportType;
    private double distance;
    private double estimatedTime;
    private String routeInfoImagePath;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourLog> tourLogs;
}
