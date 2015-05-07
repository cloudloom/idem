package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.rest.resource.AuthorityResource;

/**
 * @author sadath
 * @since 13-03-15
 */
public class AuthorityResourceBuilder {
    private String role;

    private AuthorityResourceBuilder() {

    }

    public static AuthorityResourceBuilder anAuthorityBuilder() {
        return new AuthorityResourceBuilder();
    }

    public AuthorityResourceBuilder withRole(String role){
        this.role = role;
        return this;
    }

    public AuthorityResource build(){
        AuthorityResource authority = new AuthorityResource();
        authority.setRole(this.role);
        return authority;
    }
}
