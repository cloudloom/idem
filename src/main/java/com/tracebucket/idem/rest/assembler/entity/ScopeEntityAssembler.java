package com.tracebucket.idem.rest.assembler.entity;

import com.tracebucket.idem.domain.Scope;
import com.tracebucket.idem.rest.resource.ScopeResource;
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
 * Created by Vishwajit on 04-08-2015.
 */
@Component
public class ScopeEntityAssembler extends EntityAssembler<Scope, ScopeResource> {


    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public Scope toEntity(ScopeResource resource, Class<Scope> entityClass) {
        Scope scope = null;
        if(resource != null) {
            scope = new Scope();
            if (resource.getUid() != null) {
                scope.setEntityId(new EntityId(resource.getUid()));
            }
            scope.setPassive(resource.isPassive());
            scope.setDescription(resource.getDescription());
            scope.setName(resource.getName());
        }
        return scope;
    }

    @Override
    public Set<Scope> toEntities(Collection<ScopeResource> resources, Class<Scope> entityClass) {
        Set<Scope> scopes = new HashSet<Scope>();
        if(resources != null) {
            Iterator<ScopeResource> iterator = resources.iterator();
            if(iterator.hasNext()) {
                ScopeResource groupResource = iterator.next();
                scopes.add(toEntity(groupResource, Scope.class));
            }
        }
        return scopes;
    }
}
