package be.sven.tesla.restclient;

import be.sven.tesla.json.GenericWrapper;
import be.sven.tesla.model.Response;
import be.sven.tesla.model.Vehicle;
import be.sven.tesla.model.VehicleData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

public class CommandRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandRestClient.class);

    private final RestTemplate template;

    public CommandRestClient(RestTemplate template) {
        this.template = template;
    }

    protected boolean exchange(HttpHeaders headers, String url) {
        ResponseEntity<GenericWrapper<Response>> responseEntity = template.exchange(url, HttpMethod.POST, new HttpEntity<>("", headers),
                new ParameterizedTypeReference<GenericWrapper<Response>>() {});
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            GenericWrapper<Response> response = responseEntity.getBody();
            LOGGER.info("----------------------------------- {}", responseEntity.getStatusCode());
            if (response != null) {
                LOGGER.info("Url: {} - Response: {} ", url, response.getResponse());
                return response.getResponse().isResult();
            }
            LOGGER.info("Url: {} - Response was null ", url);
        }
        LOGGER.error("Error calling {}: {}", url, responseEntity.getStatusCode());
        return false;
    }

    protected Vehicle exchangeForVehicle(HttpHeaders headers, String url) {
        ResponseEntity<GenericWrapper<Vehicle>> responseEntity = template.exchange(url, HttpMethod.POST, new HttpEntity<>("", headers),
                new ParameterizedTypeReference<GenericWrapper<Vehicle>>() {});
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            GenericWrapper<Vehicle> response = responseEntity.getBody();
            LOGGER.info("----------------------------------- {}", responseEntity.getStatusCode());
            if (response != null) {
                LOGGER.info("Url: {} - Response: {} ", url, response.getResponse());
                return response.getResponse();
            }
            LOGGER.info("Url: {} - Response was null ", url);
        }
        LOGGER.error("Error calling {}: {}", url, responseEntity.getStatusCode());
        return null;
    }
}
