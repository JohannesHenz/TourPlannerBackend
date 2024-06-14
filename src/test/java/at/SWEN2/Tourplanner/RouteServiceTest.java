package at.SWEN2.Tourplanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import at.SWEN2.Tourplanner.model.RouteInfo;
import at.SWEN2.Tourplanner.service.RouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class RouteServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RouteService routeService;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRouteInfo() throws Exception {
        String from = "12.4924,41.8902";
        String to =   "12.4944,41.8902";

        // Mock the API response
        String mockApiResponse = "{ \"routes\": [{ \"summary\": { \"distance\": 1614.0, \"duration\": 600.0 }, \"geometry\": \"encoded_geometry_here\" }] }";
        when(restTemplate.postForObject(anyString(), any(), eq(String.class))).thenReturn(mockApiResponse);

        RouteInfo routeInfo = routeService.getRouteInfo(from, to);

        assertNotNull(routeInfo);
        assertEquals(1614.0, routeInfo.getDistance());
        assertEquals(214.8, routeInfo.getDuration());
        assertEquals("[[12.492379,41.889265],[12.491745,41.889273],[12.491618,41.889265],[12.491361,41.88922],[12.491129,41.889145],[12.490943,41.889064],[12.490771,41.888961],[12.490404,41.888689],[12.490307,41.888589],[12.490244,41.888482],[12.490042,41.887861],[12.489299,41.885299],[12.489194,41.884897],[12.489153,41.884732],[12.489056,41.884586],[12.488964,41.884368],[12.488987,41.884271],[12.48905,41.884184],[12.489144,41.88429],[12.489305,41.884534],[12.489375,41.884761],[12.48941,41.884973],[12.489432,41.885256],[12.490185,41.887838],[12.490374,41.888457],[12.490411,41.888532],[12.490512,41.888629],[12.490838,41.888882],[12.490985,41.888975],[12.491134,41.889043],[12.491413,41.889131],[12.4916,41.889168],[12.491887,41.889186],[12.492929,41.889159],[12.493224,41.889206],[12.49344,41.889266],[12.493666,41.889388],[12.493737,41.889445],[12.493831,41.889572],[12.493904,41.889701],[12.494004,41.889955],[12.494084,41.890079],[12.494228,41.890147],[12.49429,41.890194],[12.494303,41.890224]]", routeInfo.getGeometry());

    }
}
