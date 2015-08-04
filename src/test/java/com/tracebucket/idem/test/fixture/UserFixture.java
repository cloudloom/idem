package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.test.builder.UserBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ssm
 * @since 13-03-15
 */
public class UserFixture {
    public static User standardUser() {
/*        Set<Authority> authorities = new HashSet<Authority>();
        authorities.add(AuthorityFixture.userAuthority());*/
        User user = UserBuilder.anUserBuilder()
                //.withUsername("user")
                .withUsername(UUID.randomUUID().toString())
                .withPassword("password")
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true)
                .withEnabled(true)
                .build();
        return user;
    }
}