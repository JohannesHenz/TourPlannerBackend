package at.SWEN2.Tourplanner.repository;

import at.SWEN2.Tourplanner.model.TourLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourLogRepository extends JpaRepository<TourLog, String> {
    List<TourLog> findByTourId(String tourId);
}
