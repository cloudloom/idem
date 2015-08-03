package com.tracebucket.idem.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;

/**
 * Created by Vishwajit on 03-08-2015.
 */
public class TenantResource extends BaseResource {

    private String name;
    private String description;
    private String logo;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
