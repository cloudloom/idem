package com.tracebucket.idem.test.config;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

/**
 * Created by sadath on 15-May-15.
 */
@Configuration
public class AccessTokenReceiverConfig {
    private static final Logger log = LoggerFactory.getLogger(AccessTokenReceiverConfig.class);

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Value("${server.port}")
    private int port;

    @Value("localhost")
    private String host;

    @Value("${server.contextPath}")
    private String contextPath;

    public String receive(String clientId, String clientPassword, String userName, String password) {
        String plainCredentials = clientId+":"+clientPassword;
        byte[] plainCredentialsBytes = plainCredentials.getBytes();
        byte[] base64CredentialsBytes = Base64.encodeBase64(plainCredentialsBytes);
        String base64Credentials = new String(base64CredentialsBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OAuth2AccessToken> response = restTemplate.exchange(basePath + "/oauth/token?grant_type=password&username=" + userName + "&password=" + password, HttpMethod.POST, request, OAuth2AccessToken.class);
        OAuth2AccessToken token = response.getBody();
        if(token != null) {
            return token.getValue();
        }
        return null;
    }
}