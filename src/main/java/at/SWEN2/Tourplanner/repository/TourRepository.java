package at.SWEN2.Tourplanner.repository;

import at.SWEN2.Tourplanner.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByNameContainingIgnoreCase(String keyword);
}
