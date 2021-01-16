package be.sven.tesla.restclient.provider;

import be.sven.tesla.core.Token;
import be.sven.tesla.core.Vehicle;
import be.sven.tesla.restclient.util.Command;
import be.sven.tesla.restclient.util.HttpHeadersBuilder;
import be.sven.tesla.restclient.util.UrlBuilder;
import be.sven.tesla.restclient.VehicleControl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class VehicleControlImpl extends CommandRestClientImpl implements VehicleControl {

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
        try {
            HttpHeaders headers = headersBuilder.getHeaders(token);
            String url = urlBuilder.buildUrl(id, Command.HONK_HORN);
            return exchange(headers, url, null);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean flashLights(Token token, Long id) {
        try {
            HttpHeaders headers = headersBuilder.getHeaders(token);
            String url = urlBuilder.buildUrl(id, Command.FLASH_LIGHTS);
            return exchange(headers, url, null);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remoteStart(Token token, Long id, String password) {
        try {
            HttpHeaders headers = headersBuilder.getHeaders(token);
            Map<String, String> params = new HashMap<>();
            params.put("password", password);
            String url = urlBuilder.buildUrl(id, Command.REMOTE_START_DRIVE, params);
            return exchange(headers, url, null);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
