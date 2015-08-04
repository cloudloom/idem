package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.domain.Scope;

/**
 * Created by Vishwajit on 04-08-2015.
 */
public class ScopeBuilder {
    private String name;
    private String description;

    private ScopeBuilder(){}

    public static ScopeBuilder aScopeBuilder(){return new ScopeBuilder();}

    public ScopeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ScopeBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Scope build(){
        Scope scope = new Scope();
        scope.setName(this.name);
        scope.setDescription(this.description);

        return scope;
    }
}
