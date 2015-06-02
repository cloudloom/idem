package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.test.builder.AuthorityBuilder;

import java.util.UUID;

/**
 * @author ssm
 * @since 13-03-15
 */
public class AuthorityFixture {
    public static Authority userAuthority() {
        Authority authority = AuthorityBuilder.anAuthorityBuilder()
                .withRole("ROLE_USER")
                .build();
        return authority;
    }

    public static Authority adminAuthority() {
        Authority authority = AuthorityBuilder.anAuthorityBuilder()
                .withRole("ROLE_ADMIN")
                .build();
        return authority;
    }

    public static Authority tempAuthority() {
        Authority authority = com.tracebucket.idem.init.builder.AuthorityBuilder.anAuthorityBuilder()
                .withRole(UUID.randomUUID().toString())
                .build();
        return authority;
    }
}