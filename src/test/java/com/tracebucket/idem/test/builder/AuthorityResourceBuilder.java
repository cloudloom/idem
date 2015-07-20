package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.rest.resource.AuthorityResource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sadath
 * @since 13-03-15
 */
public class AuthorityResourceBuilder {
    private String role;
    private Set<String> scopes = new HashSet<String>(0);

    private AuthorityResourceBuilder() {

    }

    public static AuthorityResourceBuilder anAuthorityBuilder() {
        return new AuthorityResourceBuilder();
    }

    public AuthorityResourceBuilder withRole(String role){
        this.role = role;
        return this;
    }

    public AuthorityResourceBuilder withScopes(Set<String> scopes){
        this.scopes = scopes;
        return this;
    }

    public AuthorityResource build(){
        AuthorityResource authority = new AuthorityResource();
        authority.setRole(this.role);
        authority.setScopes(this.scopes);
        return authority;
    }
}
