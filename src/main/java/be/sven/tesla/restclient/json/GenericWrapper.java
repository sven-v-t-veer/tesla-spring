package be.sven.tesla.restclient.json;

import com.fasterxml.jackson.annotation.JsonProperty;


public class GenericWrapper<T> {
    @JsonProperty("response")
    private T response;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
