package com.tracebucket.idem.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;

import java.util.HashSet;
import java.util.Set;

public class AuthorityResource extends BaseResource {
    private String role;
    private Set<String> scopes = new HashSet<String>(0);

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }
}