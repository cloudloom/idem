package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.test.builder.UserBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ssm
 * @since 13-03-15
 */
public class UserFixture {
    public static User standardUser() {
/*        Set<Authority> authorities = new HashSet<Authority>();
        authorities.add(AuthorityFixture.userAuthority());*/
        Map<String, String> tenantInformation = new HashMap<String, String>();
        tenantInformation.put("TENANT_ID", "12345");
        tenantInformation.put("TENANT_NAME", "TnT");
        User user = UserBuilder.anUserBuilder()
                .withUsername("user")
                .withPassword("password")
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true)
                .withEnabled(true)
                        .withTenantInformation(tenantInformation)
                        //.withAuthorities(authorities)
                .build();
        return user;
    }
}