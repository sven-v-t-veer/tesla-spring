package be.sven.tesla.restclient.util;

import be.sven.tesla.core.Token;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class HttpHeadersBuilder {

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent","TeslaApp");
        return headers;
    }

    public HttpHeaders getHeaders(Token token) {
        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Bearer " + token.getAccessToken());
        return headers;
    }
}
