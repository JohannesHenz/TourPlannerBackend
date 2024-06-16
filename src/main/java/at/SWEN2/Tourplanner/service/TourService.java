package at.SWEN2.Tourplanner.service;

import at.SWEN2.Tourplanner.exception.ResourceNotFoundException;
import at.SWEN2.Tourplanner.model.Tour;
import at.SWEN2.Tourplanner.model.TourLog;
import at.SWEN2.Tourplanner.repository.TourLogRepository;
import at.SWEN2.Tourplanner.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {

    private final TourRepository tourRepository;
    private final TourLogRepository tourLogRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourLogRepository tourLogRepository) {
        this.tourRepository = tourRepository;
        this.tourLogRepository = tourLogRepository;
    }

    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Tour getTourById(String id) { // Change this to String
        return tourRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tour not found with id " + id));
    }

    public List<Tour> searchTours(String keyword) {
        return tourRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Tour saveTour(Tour tour) {
        return tourRepository.save(tour);
    }


    public void deleteTour(String id) { // Change this to String
        Tour tour = getTourById(id);
        tourRepository.delete(tour);
    }

    public TourLog addTourLog(String tourId, TourLog tourLog) {
        Optional<Tour> optionalTour = tourRepository.findById(tourId);
        if (optionalTour.isPresent()) {
            Tour tour = optionalTour.get();
            tour.addTourLog(tourLog);
            tourLogRepository.save(tourLog);
            return tourLog;
        } else {
            throw new ResourceNotFoundException("Tour not found with id " + tourId);
        }
    }
}
