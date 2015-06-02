package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.domain.Client;
import com.tracebucket.idem.test.builder.ClientBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author ssm
 * @since 13-03-15
 */
public class ClientFixture {
    public static Client standardClient() {

/*        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(AuthorityFixture.userAuthority());
        authorities.add(AuthorityFixture.adminAuthority());*/

        Set<String> authorizedGrantTypes = new HashSet<String>();
        authorizedGrantTypes.add("authorization_code");
        authorizedGrantTypes.add("refresh_token");
        authorizedGrantTypes.add("password");

        Set<String> scopes = new HashSet<String>();
        scopes.add("idem-read");
        scopes.add("idem-write");

        Client client = ClientBuilder.aClientBuilder()
                .withClientId("idem-admin")
                .withClientSecret("idem-admin-secret")
                        //.withAuthorities(authorities)
                .withAuthorizedGrantTypes(authorizedGrantTypes)
                .withScope(scopes)
                .build();
        return client;
    }

    public static Client tempClient() {

/*        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(AuthorityFixture.userAuthority());
        authorities.add(AuthorityFixture.adminAuthority());*/

        Set<String> authorizedGrantTypes = new HashSet<String>();
        authorizedGrantTypes.add("authorization_code");
        authorizedGrantTypes.add("refresh_token");
        authorizedGrantTypes.add("password");

        Set<String> scopes = new HashSet<String>();
        scopes.add(UUID.randomUUID().toString());
        scopes.add(UUID.randomUUID().toString());

        Client client = com.tracebucket.idem.init.builder.ClientBuilder.aClientBuilder()
                .withClientId(UUID.randomUUID().toString())
                .withClientSecret(UUID.randomUUID().toString())
                        //.withAuthorities(authorities)
                .withAuthorizedGrantTypes(authorizedGrantTypes)
                .withScope(scopes)
                .build();
        return client;
    }

}