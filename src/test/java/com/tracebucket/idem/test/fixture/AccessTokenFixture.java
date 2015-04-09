package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.domain.AccessToken;
import com.tracebucket.idem.test.builder.AccessTokenBuilder;

import java.util.*;

/**
 * @author ssm
 * @since 13-03-15
 */
public class AccessTokenFixture {
    public static AccessToken standardAccessToken() {
        Map<String, String> additionalInformation = new HashMap<String, String>();
        additionalInformation.put("Info Key1", "Info Val1");
        additionalInformation.put("Info Key2", "Info Val2");

        Set<String> scope = new HashSet<String>();
        scope.add("Scope");

        AccessToken accessToken = AccessTokenBuilder.anAccessTokenBuilder()
                .withAuthenticationId(UUID.randomUUID().toString())
                .withTokenId(UUID.randomUUID().toString())
                .withToken(UUID.randomUUID().toString().getBytes())
                .withAuthentication(UUID.randomUUID().toString().getBytes())
                .withClient(null)
                .withUser(null)
                .withAdditionalInformation(additionalInformation)
                .withExpiration(new Date())
                .withTokenType("Token Type")
                .withValue("Access Value")
                //.withRefreshToken(RefreshTokenFixture.standardRefreshToken())
                .withScope(scope)
                .build();
        return accessToken;
    }
}