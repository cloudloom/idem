package com.tracebucket.idem.rest.assembler.entity;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.Group;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.rest.resource.GroupResource;
import com.tracebucket.idem.rest.resource.UserResource;
import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.EntityAssembler;
import com.tracebucket.tron.ddd.domain.EntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by sadath on 13-May-15.
 */
@Component
public class GroupEntityAssembler extends EntityAssembler<Group, GroupResource> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public Group toEntity(GroupResource resource, Class<Group> entityClass) {
        Group group = null;
        if(resource != null) {
            group = new Group();
            if (resource.getUid() != null) {
                group.setEntityId(new EntityId(resource.getUid()));
            }
            group.setPassive(resource.isPassive());
            group.setDescription(resource.getDescription());
            group.setImage(resource.getImage());
            group.setName(resource.getName());
            group.setAuthorities(assemblerResolver.resolveEntityAssembler(Authority.class, AuthorityResource.class).toEntities(resource.getAuthorities(), Authority.class));
            //group.setMembers(assemblerResolver.resolveEntityAssembler(User.class, UserResource.class).toEntities(resource.getMembers(), User.class));
        }
        return group;
    }

    @Override
    public Set<Group> toEntities(Collection<GroupResource> resources, Class<Group> entityClass) {
        Set<Group> groups = new HashSet<Group>();
        if(resources != null) {
            Iterator<GroupResource> iterator = resources.iterator();
            if(iterator.hasNext()) {
                GroupResource groupResource = iterator.next();
                groups.add(toEntity(groupResource, Group.class));
            }
        }
        return groups;
    }
}