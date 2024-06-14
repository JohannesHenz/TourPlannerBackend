package at.SWEN2.Tourplanner.utility;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public class ORSClient {
    private final String API_KEY = "5b3ce3597851110001cf6248bc797bfd5e6d43bc8245ff9754a1bfe3";
    private final String ORS_URL = "https://api.openrouteservice.org/v2/directions/driving-car";

    public String getRoute(String start, String end) {
        String requestUrl = ORS_URL + "?api_key=" + API_KEY + "&start=" + start + "&end=" + end;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class);
        return response.getBody();
    }
}
