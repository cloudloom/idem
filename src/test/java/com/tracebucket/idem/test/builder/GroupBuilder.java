package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.Group;
import com.tracebucket.idem.domain.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 17-Mar-15.
 */
public class GroupBuilder {
    private String name;
    private String description;
    private String image;
    private Set<Authority> authorities = new HashSet<>(0);
    private Set<User> members = new HashSet<>(0);

    private GroupBuilder() {}

    public static GroupBuilder aGroupBuilder() {
        return new GroupBuilder();
    }

    public GroupBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public GroupBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public GroupBuilder withImage(String image) {
        this.image = image;
        return this;
    }

    public GroupBuilder withAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public GroupBuilder withMembers(Set<User> members) {
        this.members = members;
        return this;
    }

    public Group build() {
        Group group = new Group();
        group.setAuthorities(this.authorities);
        group.setDescription(this.description);
        group.setImage(this.image);
        group.setMembers(this.members);
        group.setName(this.name);
        return group;
    }
}