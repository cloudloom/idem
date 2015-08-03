package com.tracebucket.idem.domain;

import com.tracebucket.tron.ddd.domain.BaseEntity;

import javax.persistence.*;

/**
 * Created by Vishwajit on 03-08-2015.
 */
@Entity(name = "IDEM_TENANT")
@Table(name = "IDEM_TENANT")
public class Tenant extends BaseEntity{

    @Column(name = "NAME", unique = true, nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String name;

    @Column(name = "DESCRIPTION")
    @Basic(fetch = FetchType.EAGER)
    private String description;

    @Column(name = "LOGO")
    @Basic(fetch = FetchType.EAGER)
    private String logo;

    @Column(name = "URL", unique = true, nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String url;

    public Tenant() {
    }

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
