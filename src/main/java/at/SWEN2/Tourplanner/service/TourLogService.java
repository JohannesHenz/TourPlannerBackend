package at.SWEN2.Tourplanner.service;

import at.SWEN2.Tourplanner.model.TourLog;
import at.SWEN2.Tourplanner.repository.TourLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourLogService {
    @Autowired
    private TourLogRepository tourLogRepository;

    public List<TourLog> getTourLogsByTourId(Long tourId) {
        return tourLogRepository.findByTourId(tourId);
    }

    public TourLog saveTourLog(TourLog tourLog) {
        return tourLogRepository.save(tourLog);
    }

    public void deleteTourLog(Long id) {
        tourLogRepository.deleteById(id);
    }
}
