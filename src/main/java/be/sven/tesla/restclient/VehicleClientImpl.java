package be.sven.tesla.restclient;

import be.sven.tesla.json.GenericWrapper;
import be.sven.tesla.json.VehicleList;
import be.sven.tesla.model.Token;
import be.sven.tesla.model.Vehicle;
import be.sven.tesla.model.VehicleData;
import be.sven.tesla.spi.VehicleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Component
public class VehicleClientImpl implements VehicleClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleClientImpl.class);

    private final RestTemplate template;
    private final HttpHeadersBuilder headersBuilder;
    private final UrlBuilder urlBuilder;

    @Autowired
    public VehicleClientImpl(RestTemplate template, HttpHeadersBuilder headersBuilder, UrlBuilder urlBuilder) {
        this.template = template;
        this.headersBuilder = headersBuilder;
        this.urlBuilder = urlBuilder;
    }

    @Override
    public List<Vehicle> getVehicles(Token token) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl();
        ResponseEntity<VehicleList> response = template.exchange(url, HttpMethod.GET, new HttpEntity<>("", headers), VehicleList.class);
        VehicleList vehicles = response.getBody();
        LOGGER.info("----------------------------------- {}", response.getStatusCode());
        LOGGER.info("Vehicles: {}", vehicles);
        return vehicles != null ? vehicles.getVehicles() : Collections.emptyList();
    }


    @Override
    public Vehicle getVehicle(Token token, Long id) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id);
        ResponseEntity<GenericWrapper<Vehicle>> response = template.exchange(url, HttpMethod.GET,
                new HttpEntity<>("", headers), new ParameterizedTypeReference<GenericWrapper<Vehicle>>() {});
        GenericWrapper<Vehicle> wrapper = response.getBody();
        LOGGER.info("----------------------------------- {}", response.getStatusCode());
        LOGGER.info("Vehicle: {}", wrapper);
        return wrapper.getResponse();
    }

}
