package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.domain.Scope;
import com.tracebucket.idem.test.builder.ScopeBuilder;

import java.util.UUID;

/**
 * Created by Vishwajit on 04-08-2015.
 */
public class ScopeFixture {

    public static Scope standardScope() {
        Scope scope = ScopeBuilder.aScopeBuilder()
                .withDescription("Description")
                .withName(UUID.randomUUID().toString())
                .build();
        return scope;
    }
}
