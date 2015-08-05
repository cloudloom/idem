package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.Group;
import com.tracebucket.idem.domain.Tenant;
import com.tracebucket.idem.domain.User;

import java.util.*;

/**
 * @author ssm
 * @since 13-03-15
 */
public class UserBuilder {
    private String password;
    private String username;
    private Set<Authority> authorities = Collections.emptySet();
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Set<Group> groups = new HashSet<>(0);
    private Set<Tenant> tenantInformation = new HashSet<Tenant>(0);

    private UserBuilder() {}

    public static UserBuilder anUserBuilder() {
        return new UserBuilder();
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public UserBuilder withAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
        return this;
    }

    public UserBuilder withAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
        return this;
    }

    public UserBuilder withCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
        return this;
    }

    public UserBuilder withEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public UserBuilder withGroups(Set<Group> groups) {
        this.groups = groups;
        return this;
    }

    public UserBuilder withTenantInformation(Set<Tenant> tenantInformation) {
        this.tenantInformation = tenantInformation;
        return this;
    }

    public User build() {
        User user = new User(this.username, this.password, this.enabled, this.accountNonExpired, this.credentialsNonExpired, this.accountNonLocked, this.authorities);
        user.setAdditionalInformation(this.tenantInformation);
        user.setGroups(this.groups);
        return user;
    }
}