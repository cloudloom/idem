package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.rest.resource.AuthorizationCodeResource;

/**
 * @author ssm
 * @since 13-03-15
 */
public class AuthorizationCodeResourceBuilder {
    private String code;
    private byte[] authentication;

    private AuthorizationCodeResourceBuilder(){ }

    public static AuthorizationCodeResourceBuilder anAuthorizationCodeBuilder() {
        return new AuthorizationCodeResourceBuilder();
    }

    public AuthorizationCodeResourceBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public AuthorizationCodeResourceBuilder withAuthentication(byte[] authentication) {
        this.authentication = authentication;
        return this;
    }

    public AuthorizationCodeResource build() {
        AuthorizationCodeResource authorizationCode = new AuthorizationCodeResource();
        authorizationCode.setAuthentication(this.authentication);
        authorizationCode.setCode(this.code);
        return authorizationCode;
    }
}
