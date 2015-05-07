package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.rest.resource.ClientResource;
import com.tracebucket.idem.test.builder.ClientResourceBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ssm
 * @since 13-03-15
 */
public class ClientResourceFixture {
    public static ClientResource standardClient() {

/*        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(AuthorityFixture.userAuthority());
        authorities.add(AuthorityFixture.adminAuthority());*/

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
                        //.withAuthorities(authorities)
                .withAuthorizedGrantTypes(authorizedGrantTypes)
                .withScope(scopes)
                .build();
        return client;
    }
}