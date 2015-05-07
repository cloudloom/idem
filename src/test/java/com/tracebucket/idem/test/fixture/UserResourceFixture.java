package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.rest.resource.UserResource;
import com.tracebucket.idem.test.builder.UserResourceBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ssm
 * @since 13-03-15
 */
public class UserResourceFixture {
    public static UserResource standardUser() {
/*        Set<Authority> authorities = new HashSet<Authority>();
        authorities.add(AuthorityFixture.userAuthority());*/
        Map<String, String> tenantInformation = new HashMap<String, String>();
        tenantInformation.put("TENANT_ID", "12345");
        tenantInformation.put("TENANT_NAME", "TnT");
        UserResource user = UserResourceBuilder.anUserBuilder()
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