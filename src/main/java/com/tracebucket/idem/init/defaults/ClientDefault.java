package com.tracebucket.idem.init.defaults;

import com.tracebucket.idem.domain.Client;
import com.tracebucket.idem.init.builder.ClientBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 15-May-15.
 */
public class ClientDefault {
    public static Client defaultClient() {
        Set<String> scopes = new HashSet<String>();
        scopes.add("idem-read");
        scopes.add("idem-write");
        Client client = ClientBuilder.aClientBuilder()
                .withClientId("idem-admin")
                .withClientSecret("idem-admin-secret")
                .withScope(scopes)
                .build();
        return client;
    }
}