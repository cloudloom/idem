package com.tracebucket.idem.init.builder;

import com.tracebucket.idem.domain.Tenant;

/**
 * Created by Vishwajit on 03-08-2015.
 */
public class TenantBuilder {

    private String name;
    private String description;
    private String logo;
    private String url;

    private TenantBuilder(){}

    public static TenantBuilder aTenantBuilder(){
        return new TenantBuilder();
    }

    public TenantBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TenantBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TenantBuilder withLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public TenantBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public Tenant build() {
        Tenant tenant = new Tenant();
        tenant.setName(this.name);
        tenant.setDescription(this.description);
        tenant.setLogo(this.logo);
        tenant.setUrl(this.url);
        return tenant;
    }
}
