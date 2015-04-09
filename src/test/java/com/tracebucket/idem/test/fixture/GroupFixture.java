package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.Group;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.test.builder.GroupBuilder;

import java.util.HashSet;

/**
 * @author ssm
 * @since 17-03-15
 */
public class GroupFixture {
    public static Group standardGroup() {
        Group group = GroupBuilder.aGroupBuilder()
                .withAuthorities(new HashSet<Authority>(0))
                .withMembers(new HashSet<User>(0))
                .withImage("Image")
                .withDescription("Description")
                .withName("Name")
                .build();
        return group;
    }
}
