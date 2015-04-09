package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.domain.RefreshToken;
import com.tracebucket.idem.test.builder.RefreshTokenBuilder;

import java.util.UUID;

/**
 * @author ssm
 * @since 13-03-15
 */
public class RefreshTokenFixture {
    public static RefreshToken standardRefreshToken() {
        RefreshToken refreshToken = RefreshTokenBuilder.aRefreshTokenBuilder()
                .withAuthentication(UUID.randomUUID().toString().getBytes())
                .withToken(UUID.randomUUID().toString().getBytes())
                .withTokenId(UUID.randomUUID().toString())
                .withValue("Value")
                .build();
        return refreshToken;
    }
}