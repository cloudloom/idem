package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.domain.AuthorizationCode;

/**
 * @author ssm
 * @since 13-03-15
 */
public class AuthorizationCodeBuilder {
    private String code;
    private byte[] authentication;

    private AuthorizationCodeBuilder(){ }

    public static AuthorizationCodeBuilder anAuthorizationCodeBuilder() {
        return new AuthorizationCodeBuilder();
    }

    public AuthorizationCodeBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public AuthorizationCodeBuilder withAuthentication(byte[] authentication) {
        this.authentication = authentication;
        return this;
    }

    public AuthorizationCode build() {
        AuthorizationCode authorizationCode = new AuthorizationCode(this.code, this.authentication);
        return authorizationCode;
    }
}
