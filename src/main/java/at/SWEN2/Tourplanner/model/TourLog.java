package at.SWEN2.Tourplanner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tour_log")
@Data
public class TourLog {

    @Id
    @Column(name = "log_id")
    private String logId = UUID.randomUUID().toString();;



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

    public Tour getTour() {
        return this.tour;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }


}
