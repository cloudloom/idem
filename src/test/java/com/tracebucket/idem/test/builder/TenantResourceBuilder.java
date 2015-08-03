package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.rest.resource.TenantResource;

/**
 * Created by Vishwajit on 03-08-2015.
 */
public class TenantResourceBuilder {

    private String name;
    private String description;
    private String logo;
    private String url;

    private TenantResourceBuilder(){}

    public static TenantResourceBuilder aTenantBuilder(){
        return new TenantResourceBuilder();
    }

    public TenantResourceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TenantResourceBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TenantResourceBuilder withLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public TenantResourceBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public TenantResource build() {
        TenantResource tenantResource = new TenantResource();
        tenantResource.setName(this.name);
        tenantResource.setDescription(this.description);
        tenantResource.setLogo(this.logo);
        tenantResource.setUrl(this.url);
        return tenantResource;
    }
}
