package at.SWEN2.Tourplanner.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class TourLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    private LocalDateTime dateTime;
    private String comment;
    private int difficulty;
    private int totalTime;
    private int rating;
}
