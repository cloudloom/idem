package com.tracebucket.idem.rest.assembler.entity;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.Group;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.repository.jpa.GroupRepository;
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
public class UserEntityAssembler extends EntityAssembler<User, UserResource> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public User toEntity(UserResource resource, Class<User> entityClass) {
        User user = null;
        if(resource != null) {
            user = new User();
            if (resource.getUid() != null) {
                user.setEntityId(new EntityId(resource.getUid()));
            }
            user.setPassive(resource.isPassive());
            user.setAuthorities(assemblerResolver.resolveEntityAssembler(Authority.class, AuthorityResource.class).toEntities(resource.getAuthorities(), Authority.class));
            user.setGroups(assemblerResolver.resolveEntityAssembler(Group.class, GroupResource.class).toEntities(resource.getGroups(), Group.class));
            user.setAdditionalInformation(resource.getTenantInformation());
            user.setAccountNonExpired(resource.isAccountNonExpired());
            user.setAccountNonLocked(resource.isAccountNonLocked());
            user.setCredentialsNonExpired(resource.isCredentialsNonExpired());
            user.setEnabled(resource.isEnabled());
            user.setPassword(resource.getPassword());
            user.setUsername(resource.getUsername());
        }
        return user;
    }

    @Override
    public Set<User> toEntities(Collection<UserResource> resources, Class<User> entityClass) {
        Set<User> users = new HashSet<User>();
        if(resources != null) {
            Iterator<UserResource> iterator = resources.iterator();
            if(iterator.hasNext()) {
                UserResource userResource = iterator.next();
                users.add(toEntity(userResource, User.class));
            }
        }
        return users;    }
}