package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.rest.resource.TenantResource;
import com.tracebucket.idem.test.builder.TenantResourceBuilder;

import java.util.UUID;

/**
 * Created by Vishwajit on 03-08-2015.
 */
public class TenantResourceFixture {
    public static TenantResource standardTenant() {
        TenantResource tenant = TenantResourceBuilder.aTenantBuilder()
                .withDescription("Resource_Description")
                .withLogo("Resource_Logo")
                .withUrl("www"+UUID.randomUUID().toString())
                .withName(UUID.randomUUID().toString())
                .build();
        return tenant;
    }
}
