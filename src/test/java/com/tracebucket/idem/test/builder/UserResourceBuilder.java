package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.rest.resource.GroupResource;
import com.tracebucket.idem.rest.resource.UserResource;

import java.util.*;

/**
 * @author ssm
 * @since 13-03-15
 */
public class UserResourceBuilder {
    private String password;
    private String username;
    private Set<AuthorityResource> authorities = Collections.emptySet();
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Set<GroupResource> groups = new HashSet<>(0);
    private Map<String, String> tenantInformation = new LinkedHashMap<String, String>();

    private UserResourceBuilder() {}

    public static UserResourceBuilder anUserBuilder() {
        return new UserResourceBuilder();
    }

    public UserResourceBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserResourceBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserResourceBuilder withAuthorities(Set<AuthorityResource> authorities) {
        this.authorities = authorities;
        return this;
    }

    public UserResourceBuilder withAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
        return this;
    }

    public UserResourceBuilder withAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
        return this;
    }

    public UserResourceBuilder withCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
        return this;
    }

    public UserResourceBuilder withEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public UserResourceBuilder withGroups(Set<GroupResource> groups) {
        this.groups = groups;
        return this;
    }

    public UserResourceBuilder withTenantInformation(Map<String, String> tenantInformation) {
        this.tenantInformation = tenantInformation;
        return this;
    }

    public UserResource build() {
        UserResource user = new UserResource();
        user.setAccountNonExpired(this.accountNonExpired);
        user.setAccountNonLocked(this.accountNonLocked);
        user.setAuthorities(this.authorities);
        user.setCredentialsNonExpired(this.credentialsNonExpired);
        user.setEnabled(this.enabled);
        user.setGroups(this.groups);
        user.setPassword(this.password);
        user.setUsername(this.username);
        user.setTenantInformation(this.tenantInformation);
        return user;
    }
}