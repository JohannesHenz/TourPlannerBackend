package at.SWEN2.Tourplanner.service;

import at.SWEN2.Tourplanner.model.Tour;
import at.SWEN2.Tourplanner.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourService {
    @Autowired
    private TourRepository tourRepository;

    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Tour getTourById(Long id) {
        return tourRepository.findById(id).orElse(null);
    }

    public List<Tour> searchTours(String keyword) {
        return tourRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Tour saveTour(Tour tour) {
        return tourRepository.save(tour);
    }

    public void deleteTour(Long id) {
        tourRepository.deleteById(id);
    }

    // Additional methods for calculated attributes...
}
