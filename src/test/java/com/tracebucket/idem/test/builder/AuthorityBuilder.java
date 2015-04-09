package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.domain.Authority;

/**
 * Created by sadath on 13-Mar-15.
 */
public class AuthorityBuilder {
    private String role;

    private AuthorityBuilder() {

    }

    public static AuthorityBuilder anAuthorityBuilder() {
        return new AuthorityBuilder();
    }

    public AuthorityBuilder withRole(String role){
        this.role = role;
        return this;
    }

    public Authority build(){
        Authority authority = new Authority(this.role);
        return authority;
    }
}
