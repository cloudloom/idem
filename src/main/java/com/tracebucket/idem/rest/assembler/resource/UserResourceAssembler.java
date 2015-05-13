package com.tracebucket.idem.rest.assembler.resource;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.Group;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.rest.resource.GroupResource;
import com.tracebucket.idem.rest.resource.UserResource;
import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.ResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by sadath on 13-May-15.
 */
@Component
public class UserResourceAssembler extends ResourceAssembler<UserResource, User> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public UserResource toResource(User entity, Class<UserResource> resourceClass) {
        UserResource user = null;
        try {
            user = resourceClass.newInstance();
            if (entity != null) {
                Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) entity.getAuthorities();
                if(authorities != null && authorities.size() > 0) {
                    Set<Authority> authorityList = new HashSet<Authority>();
                    authorities.stream().forEach(a -> authorityList.add((Authority)a));
                    user.setAuthorities(assemblerResolver.resolveResourceAssembler(AuthorityResource.class, Authority.class).toResources(authorityList, AuthorityResource.class));
                }
                Map<String, Object> tenantInformation = entity.getTenantInformation();
                Map<String, String> tenantInformation2 = new HashMap<String, String>();
                Set<Map.Entry<String, Object>> entries = tenantInformation.entrySet();
                entries.forEach(e -> tenantInformation2.put(e.getKey(), (String)e.getValue()));
                user.setUid(entity.getEntityId().getId());
                user.setPassive(entity.isPassive());
                user.setGroups(assemblerResolver.resolveResourceAssembler(GroupResource.class, Group.class).toResources(entity.getGroups(), GroupResource.class));
                user.setTenantInformation(tenantInformation2);
                user.setAccountNonExpired(entity.isAccountNonExpired());
                user.setAccountNonLocked(entity.isAccountNonLocked());
                user.setCredentialsNonExpired(entity.isCredentialsNonExpired());
                user.setEnabled(entity.isEnabled());
                user.setPassword(entity.getPassword());
                user.setUsername(entity.getUsername());
            }
        } catch (InstantiationException ie) {

        } catch (IllegalAccessException iae) {

        }
        return user;
    }

    @Override
    public Set<UserResource> toResources(Collection<User> entities, Class<UserResource> resourceClass) {
        Set<UserResource> users = new HashSet<UserResource>();
        if(entities != null && entities.size() > 0) {
            Iterator<User> iterator = entities.iterator();
            if(iterator.hasNext()) {
                User user = iterator.next();
                users.add(toResource(user, UserResource.class));
            }
        }
        return users;
    }
}