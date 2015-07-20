package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.domain.Authority;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sadath
 * @since 13-03-15
 */
public class AuthorityBuilder {
    private String role;
    private Set<String> scopes = new HashSet<String>(0);

    private AuthorityBuilder() {

    }

    public static AuthorityBuilder anAuthorityBuilder() {
        return new AuthorityBuilder();
    }

    public AuthorityBuilder withRole(String role){
        this.role = role;
        return this;
    }

    public AuthorityBuilder withScopes(Set<String> scopes){
        this.scopes = scopes;
        return this;
    }

    public Authority build(){
        Authority authority = new Authority(this.role);
        authority.setScopes(this.scopes);
        return authority;
    }
}
