package be.sven.tesla.restclient;

import be.sven.tesla.json.GenericWrapper;
import be.sven.tesla.model.*;
import be.sven.tesla.spi.ClimateControl;
import com.neovisionaries.ws.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ClimateControlImpl implements ClimateControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClimateControlImpl.class);

    private final RestTemplate template;
    private final HttpHeadersBuilder headersBuilder;
    private final UrlBuilder urlBuilder;

    @Autowired
    public ClimateControlImpl(RestTemplate template, HttpHeadersBuilder headersBuilder, UrlBuilder urlBuilder) {
        this.template = template;
        this.headersBuilder = headersBuilder;
        this.urlBuilder = urlBuilder;
    }

    @Override
    public boolean start(Token token, Long id) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id, Command.CLIMATE_START);
        return exchange(headers, url);
    }

    @Override
    public boolean stop(Token token, Long id) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id, Command.CLIMATE_STOP);
        return exchange(headers, url);
    }

    @Override
    public boolean setTemp(Token token, Long id, BigDecimal temp) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        Map<String, String> params = new HashMap<>();
        params.put("driver_temp", temp.toPlainString());
        params.put("passenger_temp", temp.toPlainString());
        String url = urlBuilder.buildUrl(id, Command.CLIMATE_SET_TEMP);
        return exchange(headers, url);
    }

    @Override
    public boolean defrost(Token token, Long id, boolean on) {
        return false;
    }

    @Override
    public boolean seatHeater(Token token, Long id, Seat seat, SeatHeaterLevel level) {
        return false;
    }

    @Override
    public boolean steeringWheelHeater(Token token, Long id, boolean on) {
        return false;
    }

    private boolean exchange(HttpHeaders headers, String url) {
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
}
