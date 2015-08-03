package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.domain.Tenant;
import com.tracebucket.idem.test.builder.TenantBuilder;

import java.util.UUID;

/**
 * Created by Vishwajit on 03-08-2015.
 */
public class TenantFixture {

    public static Tenant standardTenant() {
        Tenant tenant = TenantBuilder.aTenantBuilder()
                .withDescription("Description")
                .withLogo("Logo")
                .withUrl("www.mmp.nl")
                .withName(UUID.randomUUID().toString())
                .build();
        return tenant;
    }
}
