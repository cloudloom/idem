package com.tracebucket.idem.domain;

import com.tracebucket.tron.ddd.domain.BaseEntity;

import javax.persistence.*;

/**
 * Created by Vishwajit on 04-08-2015.
 */
@Entity(name = "IDEM_SCOPE")
@Table(name = "IDEM_SCOPE")
public class Scope extends BaseEntity{

    @Column(name = "NAME", unique = true, nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String name;

    @Column(name = "DESCRIPTION")
    @Basic(fetch = FetchType.EAGER)
    private String description;

    public Scope() {
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
}
