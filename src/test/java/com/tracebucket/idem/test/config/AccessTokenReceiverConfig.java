package com.tracebucket.idem.test.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
        final int timeout = 5;
        final RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(host, port, AuthScope.ANY_REALM), new UsernamePasswordCredentials(clientId, clientPassword));
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).setDefaultCredentialsProvider(credentialsProvider).build();
        final ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(client);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        OAuth2AccessToken token = restTemplate.postForObject(basePath + "/oauth/token?grant_type=password&username=" + userName + "&password=" + password, null, OAuth2AccessToken.class);
        log.info("Access Token: " + token);
        return token.getValue();
    }
}