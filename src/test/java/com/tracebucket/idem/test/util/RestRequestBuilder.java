package com.tracebucket.idem.test.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 * Created by sadath on 15-May-15.
 */
public class RestRequestBuilder {
    public static HttpEntity<Object> build(Object obj, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + accessToken);
        return new HttpEntity<Object>(obj, headers);
    }

    public static HttpEntity<String> build(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + accessToken);
        return new HttpEntity<String>(headers);
    }
}
