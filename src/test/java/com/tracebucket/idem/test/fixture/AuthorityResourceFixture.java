package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.test.builder.AuthorityResourceBuilder;

/**
 * @author ssm
 * @since 13-03-15
 */
public class AuthorityResourceFixture {
    public static AuthorityResource userAuthority() {
        AuthorityResource authority = AuthorityResourceBuilder.anAuthorityBuilder()
                .withRole("ROLE_USER")
                .build();
        return authority;
    }

    public static AuthorityResource adminAuthority() {
        AuthorityResource authority = AuthorityResourceBuilder.anAuthorityBuilder()
                .withRole("ROLE_ADMIN")
                .build();
        return authority;
    }
}