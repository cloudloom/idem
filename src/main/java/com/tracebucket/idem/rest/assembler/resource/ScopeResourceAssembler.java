package com.tracebucket.idem.rest.assembler.resource;

import com.tracebucket.idem.domain.Scope;
import com.tracebucket.idem.rest.resource.ScopeResource;
import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.ResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Vishwajit on 04-08-2015.
 */
@Component
public class ScopeResourceAssembler extends ResourceAssembler<ScopeResource, Scope> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public ScopeResource toResource(Scope entity, Class<ScopeResource> resourceClass) {
        ScopeResource scope = null;
        try {
            scope = resourceClass.newInstance();
            if (entity != null) {
                scope.setUid(entity.getEntityId().getId());
                scope.setPassive(entity.isPassive());
                scope.setDescription(entity.getDescription());
                scope.setName(entity.getName());
            }
        } catch (InstantiationException ie) {

        } catch (IllegalAccessException iae) {

        }
        return scope;
    }


    @Override
    public Set<ScopeResource> toResources(Collection<Scope> entities, Class<ScopeResource> resourceClass) {
        Set<ScopeResource> scopes = new HashSet<ScopeResource>();
        if(entities != null && entities.size() > 0) {
            Iterator<Scope> iterator = entities.iterator();
            if(iterator.hasNext()) {
                Scope scope = iterator.next();
                scopes.add(toResource(scope, ScopeResource.class));
            }
        }
        return scopes;
    }
}
