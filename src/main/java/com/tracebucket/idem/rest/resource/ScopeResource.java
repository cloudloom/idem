package com.tracebucket.idem.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;

/**
 * Created by Vishwajit on 04-08-2015.
 */
public class ScopeResource extends BaseResource{
    private String name;
    private String description;

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
