package com.tracebucket.idem.domain;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author FFL
 * @since 12-03-2015
 */
@Entity
@Table(name = "AUTHORITY")
public class Authority extends BaseEntity implements GrantedAuthority{

    @Column(name = "ROLE", unique = true, nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String role;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "AUTHORITY_SCOPE", joinColumns = @JoinColumn(name = "AUTHORITY__ID"))
    private Set<String> scopes = new HashSet<String>(0);

    public Authority() {   }

    public Authority(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Authority) {
            return role.equals(((Authority) obj).role);
        }

        return false;
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }
}
