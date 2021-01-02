package be.sven.tesla.restclient;

import be.sven.tesla.model.Token;
import be.sven.tesla.model.Vehicle;
import be.sven.tesla.spi.VehicleControl;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class VehicleControlImpl extends CommandRestClient implements VehicleControl {

    private final HttpHeadersBuilder headersBuilder;
    private final UrlBuilder urlBuilder;

    public VehicleControlImpl(RestTemplate template, HttpHeadersBuilder headersBuilder, UrlBuilder urlBuilder) {
        super(template);
        this.headersBuilder = headersBuilder;
        this.urlBuilder = urlBuilder;
    }

    @Override
    public boolean wakeUp(Token token, Long id) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id, Command.WAKE);
        Vehicle vehicle = exchangeForVehicle(headers, url);
        return vehicle.getState().equals("online");
    }

    @Override
    public boolean honkHorn(Token token, Long id) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id, Command.HONK_HORN);
        return exchange(headers, url);
    }

    @Override
    public boolean flashLights(Token token, Long id) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id, Command.FLASH_LIGHTS);
        return exchange(headers, url);
    }

    @Override
    public boolean remoteStart(Token token, Long id, String password) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        Map<String, String> params = new HashMap<>();
        params.put("password", password);
        String url = urlBuilder.buildUrl(id, Command.REMOTE_START_DRIVE, params);
        return exchange(headers, url);
    }
}
