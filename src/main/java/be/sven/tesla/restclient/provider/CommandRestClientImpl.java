package be.sven.tesla.restclient.provider;

import be.sven.tesla.restclient.json.GenericWrapper;
import be.sven.tesla.core.Response;
import be.sven.tesla.core.Vehicle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class CommandRestClientImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandRestClientImpl.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private final RestTemplate template;

    public CommandRestClientImpl(RestTemplate template) {
        this.template = template;
    }

    protected boolean exchange(HttpHeaders headers, String url, Map<String , String> params) throws JsonProcessingException {
        String body = "";
        if (params != null && !params.isEmpty()) {
            body = mapper.writeValueAsString(mapper.valueToTree(params));
        }
        ResponseEntity<GenericWrapper<Response>> responseEntity = template.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers),
                new ParameterizedTypeReference<GenericWrapper<Response>>() {});
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            GenericWrapper<Response> response = responseEntity.getBody();
            LOGGER.info("----------------------------------- {}", responseEntity.getStatusCode());
            if (response != null) {
                LOGGER.info("Url: {} - body: {} - Response: {} ", url, body, response.getResponse());
                return response.getResponse().isResult();
            }
            LOGGER.info("Url: {} - body {} - Response was null ", url, body);
        }
        LOGGER.error("Error calling {} with body {} : {}", url, body, responseEntity.getStatusCode());
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
