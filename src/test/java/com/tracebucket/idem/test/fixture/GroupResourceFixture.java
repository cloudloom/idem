package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.rest.resource.GroupResource;
import com.tracebucket.idem.rest.resource.UserResource;
import com.tracebucket.idem.test.builder.GroupResourceBuilder;

import java.util.HashSet;
import java.util.UUID;

/**
 * @author ssm
 * @since 17-03-15
 */
public class GroupResourceFixture {
    public static GroupResource standardGroup() {
        GroupResource group = GroupResourceBuilder.aGroupBuilder()
                .withAuthorities(new HashSet<AuthorityResource>(0))
                .withMembers(new HashSet<UserResource>(0))
                .withImage("Image")
                .withDescription("Description")
                //.withName("Name")
                .withName(UUID.randomUUID().toString())
                .build();
        return group;
    }
}
