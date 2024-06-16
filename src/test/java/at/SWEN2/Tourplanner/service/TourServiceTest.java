package at.SWEN2.Tourplanner.service;

import at.SWEN2.Tourplanner.exception.ResourceNotFoundException;
import at.SWEN2.Tourplanner.model.Tour;
import at.SWEN2.Tourplanner.repository.TourRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TourServiceTest {

    @Mock
    private TourRepository tourRepository;

    @InjectMocks
    private TourService tourService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTourById() {
        Tour tour = new Tour();
        tour.setId("1");
        tour.setName("Test Tour");

        when(tourRepository.findById(anyString())).thenReturn(Optional.of(tour));

        Tour result = tourService.getTourById("1");

        assertEquals("Test Tour", result.getName());
    }

    @Test
    void testDeleteTour() {
        String tourId = "1";
        Tour tour = new Tour();
        tour.setId(tourId);

        when(tourRepository.findById(tourId)).thenReturn(Optional.of(tour));

        tourService.deleteTour(tourId);

        verify(tourRepository, times(1)).delete(tour);
    }

    @Test
    void testDeleteTourNotFound() {
        String tourId = "1";

        when(tourRepository.findById(tourId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tourService.deleteTour(tourId));
    }
}
