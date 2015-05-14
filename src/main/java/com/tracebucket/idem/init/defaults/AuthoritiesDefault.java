package com.tracebucket.idem.init.defaults;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.init.builder.AuthorityBuilder;

/**
 * @author ssm
 * @since 13-03-15
 */
public class AuthoritiesDefault {
    public static Authority idemAdministrator() {
        Authority authority = AuthorityBuilder.anAuthorityBuilder()
                .withRole("IDEM_ADMINISTRATOR")
                .build();
        return authority;
    }

    public static Authority tenantAdministrator() {
        Authority authority = AuthorityBuilder.anAuthorityBuilder()
                .withRole("TENANT_ADMINISTRATOR")
                .build();
        return authority;
    }
}