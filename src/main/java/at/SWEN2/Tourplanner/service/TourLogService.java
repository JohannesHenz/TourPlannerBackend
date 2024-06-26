package at.SWEN2.Tourplanner.service;

import at.SWEN2.Tourplanner.exception.ResourceNotFoundException;
import at.SWEN2.Tourplanner.model.TourLog;
import at.SWEN2.Tourplanner.repository.TourLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourLogService {
    @Autowired
    private TourLogRepository tourLogRepository;

    public TourLog getTourLogById(String logId) {
        return tourLogRepository.findById(logId).orElse(null);
    }


    public TourLog saveTourLog(TourLog tourLog) {
        return tourLogRepository.save(tourLog);
    }

    public void deleteTourLog(String tourId, String logId) {
        TourLog tourLog = tourLogRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("TourLog not found with id " + logId));

        if (!tourLog.getTour().getId().equals(tourId)) {
            throw new IllegalArgumentException("TourLog with id " + logId + " does not belong to Tour with id " + tourId);
        }


        tourLogRepository.delete(tourLog);
    }
}
