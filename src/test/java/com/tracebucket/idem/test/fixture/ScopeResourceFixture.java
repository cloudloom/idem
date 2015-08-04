package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.rest.resource.ScopeResource;
import com.tracebucket.idem.test.builder.ScopeResourceBuilder;

import java.util.UUID;

/**
 * Created by Vishwajit on 04-08-2015.
 */
public class ScopeResourceFixture {

    public static ScopeResource standardScopeResource() {
        ScopeResource scopeResource = ScopeResourceBuilder.aScopeResourceBuilder()
                .withDescription("Description")
                .withName(UUID.randomUUID().toString())
                .build();
        return scopeResource;
    }
}
