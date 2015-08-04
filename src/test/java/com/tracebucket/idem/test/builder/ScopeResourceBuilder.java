package com.tracebucket.idem.test.builder;


import com.tracebucket.idem.rest.resource.ScopeResource;

/**
 * Created by Vishwajit on 04-08-2015.
 */
public class ScopeResourceBuilder {
    private String name;
    private String description;

    private ScopeResourceBuilder(){}

    public static ScopeResourceBuilder aScopeResourceBuilder(){return new ScopeResourceBuilder();}

    public ScopeResourceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ScopeResourceBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ScopeResource build(){
        ScopeResource scopeResource = new ScopeResource();
        scopeResource.setName(this.name);
        scopeResource.setDescription(this.description);

        return scopeResource;
    }
}
