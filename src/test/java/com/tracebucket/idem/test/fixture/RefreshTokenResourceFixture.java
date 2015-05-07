package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.rest.resource.RefreshTokenResource;
import com.tracebucket.idem.test.builder.RefreshTokenResourceBuilder;

import java.util.UUID;

/**
 * @author ssm
 * @since 13-03-15
 */
public class RefreshTokenResourceFixture {
    public static RefreshTokenResource standardRefreshToken() {
        RefreshTokenResource refreshToken = RefreshTokenResourceBuilder.aRefreshTokenBuilder()
                .withAuthentication(UUID.randomUUID().toString().getBytes())
                .withToken(UUID.randomUUID().toString().getBytes())
                .withTokenId(UUID.randomUUID().toString())
                .withValue("Value")
                .build();
        return refreshToken;
    }
}