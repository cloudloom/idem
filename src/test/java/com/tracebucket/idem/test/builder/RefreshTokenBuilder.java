package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.domain.RefreshToken;

/**
 * Created by sadath on 13-Mar-15.
 */
public class RefreshTokenBuilder {
    private String tokenId;
    private byte[] token;
    private byte[] authentication;
    private String value;

    private RefreshTokenBuilder() {}

    public static RefreshTokenBuilder aRefreshTokenBuilder() {
        return new RefreshTokenBuilder();
    }

    public RefreshTokenBuilder withTokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public RefreshTokenBuilder withToken(byte[] token) {
        this.token = token;
        return this;
    }

    public RefreshTokenBuilder withAuthentication(byte[] authentication) {
        this.authentication = authentication;
        return this;
    }

    public RefreshTokenBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public RefreshToken build() {
        RefreshToken refreshToken = new RefreshToken(this.value);
        refreshToken.setTokenId(this.tokenId);
        refreshToken.setToken(this.token);
        refreshToken.setAuthentication(this.authentication);
        return refreshToken;
    }
}
