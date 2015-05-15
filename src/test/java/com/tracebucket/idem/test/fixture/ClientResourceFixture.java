package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.rest.resource.ClientResource;
import com.tracebucket.idem.test.builder.ClientResourceBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author ssm
 * @since 13-03-15
 */
public class ClientResourceFixture {
    public static ClientResource standardClient() {

        Set<AuthorityResource> authorities = new HashSet<>();
        authorities.add(AuthorityResourceFixture.userAuthority());
        authorities.add(AuthorityResourceFixture.adminAuthority());

        Set<String> authorizedGrantTypes = new HashSet<String>();
        authorizedGrantTypes.add("authorization_code");
        authorizedGrantTypes.add("refresh_token");
        authorizedGrantTypes.add("password");

        Set<String> scopes = new HashSet<String>();
        scopes.add("organization-read");
        scopes.add("organization-write");

        ClientResource client = ClientResourceBuilder.aClientBuilder()
                .withClientId("aurora-gateway")
                .withClientSecret("aurora-gateway-secret")
                .withAuthorities(authorities)
                .withAuthorizedGrantTypes(authorizedGrantTypes)
                .withScope(scopes)
                .build();
        return client;
    }

    public static ClientResource tempClient() {

        Set<AuthorityResource> authorities = new HashSet<>();
        authorities.add(AuthorityResourceFixture.tempAuthority());

        Set<String> authorizedGrantTypes = new HashSet<String>();
        authorizedGrantTypes.add(UUID.randomUUID().toString());
        authorizedGrantTypes.add(UUID.randomUUID().toString());
        authorizedGrantTypes.add(UUID.randomUUID().toString());

        Set<String> scopes = new HashSet<String>();
        scopes.add(UUID.randomUUID().toString());
        scopes.add(UUID.randomUUID().toString());

        ClientResource client = ClientResourceBuilder.aClientBuilder()
                .withClientId(UUID.randomUUID().toString())
                .withClientSecret(UUID.randomUUID().toString())
                .withAuthorities(authorities)
                .withAuthorizedGrantTypes(authorizedGrantTypes)
                .withScope(scopes)
                .build();
        return client;
    }
}