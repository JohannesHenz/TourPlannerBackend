package at.SWEN2.Tourplanner;

import at.SWEN2.Tourplanner.model.Tour;
import at.SWEN2.Tourplanner.service.TourService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TourServiceTest {
    @Autowired
    private TourService tourService;

    @Test
    public void testCreateTour() {
        Tour tour = new Tour();
        tour.setName("Test Tour");
        Tour savedTour = tourService.saveTour(tour);
        assertNotNull(savedTour.getId());
    }

    // Additional tests...
}
