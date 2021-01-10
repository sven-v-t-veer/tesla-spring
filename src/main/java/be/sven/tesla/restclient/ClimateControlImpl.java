package be.sven.tesla.restclient;

import be.sven.tesla.model.Seat;
import be.sven.tesla.model.SeatHeaterLevel;
import be.sven.tesla.model.Token;
import be.sven.tesla.spi.ClimateControl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class ClimateControlImpl extends CommandRestClient implements ClimateControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClimateControlImpl.class);


    private final HttpHeadersBuilder headersBuilder;
    private final UrlBuilder urlBuilder;

    @Autowired
    public ClimateControlImpl(RestTemplate template, HttpHeadersBuilder headersBuilder, UrlBuilder urlBuilder) {
        super(template);
        this.headersBuilder = headersBuilder;
        this.urlBuilder = urlBuilder;
    }

    @Override
    public boolean start(Token token, Long id) {
        try {
            HttpHeaders headers = headersBuilder.getHeaders(token);
            String url = urlBuilder.buildUrl(id, Command.CLIMATE_START);
            return exchange(headers, url, null);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean stop(Token token, Long id) {
        try {
            HttpHeaders headers = headersBuilder.getHeaders(token);
            String url = urlBuilder.buildUrl(id, Command.CLIMATE_STOP);
            return exchange(headers, url, null);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean setTemp(Token token, Long id, BigDecimal temp) {
        try {
            HttpHeaders headers = headersBuilder.getHeaders(token);
            Map<String, String> params = new HashMap<>();
            params.put("driver_temp", temp.toPlainString());
            params.put("passenger_temp", temp.toPlainString());
            String url = urlBuilder.buildUrl(id, Command.CLIMATE_SET_TEMP, params);
            return exchange(headers, url, params);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean defrost(Token token, Long id, boolean on) {
        try {
            HttpHeaders headers = headersBuilder.getHeaders(token);
            Map<String, String> params = new HashMap<>();
            params.put("on", String.valueOf(on));
            String url = urlBuilder.buildUrl(id, Command.CLIMATE_DEFROST, params);
            return exchange(headers, url, null);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean seatHeater(Token token, Long id, Seat seat, SeatHeaterLevel level) {
        try {
            HttpHeaders headers = headersBuilder.getHeaders(token);
            Map<String, String> params = new HashMap<>();
            params.put("level", String.valueOf(level.ordinal()));
            params.put("heater", seat.toString());
            String url = urlBuilder.buildUrl(id, Command.CLIMATE_SEAT_HEATER, params);
            return exchange(headers, url, null);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean steeringWheelHeater(Token token, Long id, boolean on) {
        try {
            HttpHeaders headers = headersBuilder.getHeaders(token);
            Map<String, String> params = new HashMap<>();
            params.put("on", String.valueOf(on));
            String url = urlBuilder.buildUrl(id, Command.CLIMATE_STEERING_WHEEL, params);
            return exchange(headers, url, null);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }


}
