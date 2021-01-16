package be.sven.tesla.restclient;

import be.sven.tesla.exception.AuthenticationException;
import be.sven.tesla.core.Token;

public interface OAuthHttpClient {

    Token getAccessToken(int id) throws AuthenticationException;
    Token getAccessToken(String email, String password) throws AuthenticationException;
    Token refreshToken(Token token) throws AuthenticationException;
}
