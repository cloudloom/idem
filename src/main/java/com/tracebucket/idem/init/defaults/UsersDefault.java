package com.tracebucket.idem.init.defaults;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.init.builder.UserBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ssm
 * @since 13-03-15
 */
public class UsersDefault {
    public static User defaultIdemAdministrator() {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(AuthoritiesDefault.idemAdministrator());
        User user = UserBuilder.anUserBuilder()
                .withUsername("admin")
                .withPassword("admin")
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true)
                .withEnabled(true)
                .withAuthorities(authorities)
                .build();
        return user;
    }

    public static User defaultIdemAdministrator(Set<Authority> authorities) {
        User user = UserBuilder.anUserBuilder()
                .withUsername("admin")
                .withPassword("admin")
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true)
                .withEnabled(true)
                .withAuthorities(authorities)
                .build();
        return user;
    }

    public static User defaultTenantAdministrator() {
                Set<Authority> authorities = new HashSet<>();
                authorities.add(AuthoritiesDefault.tenantAdministrator());
                User user = UserBuilder.anUserBuilder()
                        .withUsername("tenant")
                        .withPassword("tenant")
                        .withAccountNonExpired(true)
                        .withAccountNonLocked(true)
                        .withCredentialsNonExpired(true)
                        .withEnabled(true)
                        .withAuthorities(authorities)
                        .build();
                return user;
        }

    public static User defaultTenantAdministrator(Set<Authority> authorities) {
        User user = UserBuilder.anUserBuilder()
                .withUsername("tenant")
                .withPassword("tenant")
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true)
                .withEnabled(true)
                .withAuthorities(authorities)
                .build();
        return user;
    }
}