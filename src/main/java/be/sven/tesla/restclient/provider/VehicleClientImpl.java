package be.sven.tesla.restclient.provider;

import be.sven.tesla.restclient.json.GenericWrapper;
import be.sven.tesla.restclient.json.VehicleList;
import be.sven.tesla.core.Token;
import be.sven.tesla.core.Vehicle;
import be.sven.tesla.restclient.util.HttpHeadersBuilder;
import be.sven.tesla.restclient.util.UrlBuilder;
import be.sven.tesla.restclient.VehicleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
    public Vehicle getVehicle(Token token, Long id) { //408 REQUEST_TIMEOUT the car is asleep
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id);
        ResponseEntity<GenericWrapper<Vehicle>> response = template.exchange(url, HttpMethod.GET,
                new HttpEntity<>("", headers), new ParameterizedTypeReference<GenericWrapper<Vehicle>>() {});
        LOGGER.info("----------------------------------- {}", response.getStatusCode());
        if (response.getStatusCode() == HttpStatus.OK) {
            GenericWrapper<Vehicle> wrapper = response.getBody();
            LOGGER.info("Vehicle: {}", wrapper);
            if (wrapper == null) {
                //throw an exception here.
            }
            return wrapper.getResponse();
        }
        if (response.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
            // car is asleep?
        }
        return null;
    }

    @Override
    public boolean isVehicleOnline(Token token, Long id) {
        Vehicle vehicle = getVehicle(token, id);
        return "online".equals(vehicle.getState());
    }
}
