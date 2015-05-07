package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.rest.resource.GroupResource;
import com.tracebucket.idem.rest.resource.UserResource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ssm
 * @since 17-03-15
 */
public class GroupResourceBuilder {
    private String name;
    private String description;
    private String image;
    private Set<AuthorityResource> authorities = new HashSet<>(0);
    private Set<UserResource> members = new HashSet<>(0);

    private GroupResourceBuilder() {}

    public static GroupResourceBuilder aGroupBuilder() {
        return new GroupResourceBuilder();
    }

    public GroupResourceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public GroupResourceBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public GroupResourceBuilder withImage(String image) {
        this.image = image;
        return this;
    }

    public GroupResourceBuilder withAuthorities(Set<AuthorityResource> authorities) {
        this.authorities = authorities;
        return this;
    }

    public GroupResourceBuilder withMembers(Set<UserResource> members) {
        this.members = members;
        return this;
    }

    public GroupResource build() {
        GroupResource group = new GroupResource();
        group.setAuthorities(this.authorities);
        group.setDescription(this.description);
        group.setImage(this.image);
        group.setMembers(this.members);
        group.setName(this.name);
        return group;
    }
}