package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.rest.resource.AuthorizationCodeResource;
import com.tracebucket.idem.test.builder.AuthorizationCodeResourceBuilder;

/**
 * @author ssm
 * @since 13-03-15
 */
public class AuthorizationCodeResourceFixture {
    public static AuthorizationCodeResource standardAuthorizationCode() {
        AuthorizationCodeResource authorizationCode = AuthorizationCodeResourceBuilder.anAuthorizationCodeBuilder()
                .withCode("Code")
                .withAuthentication("AuthorizationCodeResource".getBytes())
                .build();
        return authorizationCode;
    }
}