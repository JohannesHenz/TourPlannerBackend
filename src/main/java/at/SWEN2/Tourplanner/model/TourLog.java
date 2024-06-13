package at.SWEN2.Tourplanner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class TourLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private Double time;

    @Column(name = "comment")
    private String comment;

    @Column(name = "difficulty")
    private Double difficulty;

    @Column(name = "total_distance")
    private Double totalDistance;

    @Column(name = "total_time")
    private Double totalTime;

    @Column(name = "rating")
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    @JsonBackReference
    private Tour tour;


}
