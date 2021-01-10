package be.sven.tesla.spi;

import be.sven.tesla.exception.AuthenticationException;
import be.sven.tesla.model.Token;

public interface OAuthHttpClient {

    Token getAccessToken(int id) throws AuthenticationException;
    Token getAccessToken(String email, String password) throws AuthenticationException;
    Token refreshToken(Token token) throws AuthenticationException;
}
