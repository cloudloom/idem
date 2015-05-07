package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.rest.resource.RefreshTokenResource;

/**
 * @author ssm
 * @since 13-03-15
 */
public class RefreshTokenResourceBuilder {
    private String tokenId;
    private byte[] token;
    private byte[] authentication;
    private String value;

    private RefreshTokenResourceBuilder() {}

    public static RefreshTokenResourceBuilder aRefreshTokenBuilder() {
        return new RefreshTokenResourceBuilder();
    }

    public RefreshTokenResourceBuilder withTokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public RefreshTokenResourceBuilder withToken(byte[] token) {
        this.token = token;
        return this;
    }

    public RefreshTokenResourceBuilder withAuthentication(byte[] authentication) {
        this.authentication = authentication;
        return this;
    }

    public RefreshTokenResourceBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public RefreshTokenResource build() {
        RefreshTokenResource refreshToken = new RefreshTokenResource();
        refreshToken.setValue(this.value);
        refreshToken.setTokenId(this.tokenId);
        refreshToken.setToken(this.token);
        refreshToken.setAuthentication(this.authentication);
        return refreshToken;
    }
}
