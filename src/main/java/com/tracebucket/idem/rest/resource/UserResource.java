package com.tracebucket.idem.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class UserResource extends BaseResource {
    private String password;
    private String username;
    private Set<AuthorityResource> authorities = new HashSet<AuthorityResource>(0);
    private Set<GroupResource> groups = new HashSet<>(0);
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Set<TenantResource> tenantInformation = new HashSet<TenantResource>();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<AuthorityResource> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityResource> authorities) {
        this.authorities = authorities;
    }

    public Set<GroupResource> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupResource> groups) {
        this.groups = groups;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<TenantResource> getTenantInformation() {
        return tenantInformation;
    }

    public void setTenantInformation(Set<TenantResource> tenantInformation) {
        this.tenantInformation = tenantInformation;
    }
}