package com.tracebucket.idem.rest.assembler.entity;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.Tenant;
import com.tracebucket.idem.rest.resource.TenantResource;
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
 * Created by Vishwajit on 03-08-2015.
 */
@Component
public class TenantEntityAssembler extends EntityAssembler<Tenant, TenantResource> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public Tenant toEntity(TenantResource resource, Class<Tenant> entityClass) {
        Tenant tenant = null;
        if(resource != null) {
            tenant = new Tenant();
            if (resource.getUid() != null) {
                tenant.setEntityId(new EntityId(resource.getUid()));
            }
            tenant.setPassive(resource.isPassive());
            tenant.setDescription(resource.getDescription());
            tenant.setLogo(resource.getLogo());
            tenant.setName(resource.getName());
            tenant.setUrl(resource.getUrl());
        }
        return tenant;
    }

    @Override
    public Set<Tenant> toEntities(Collection<TenantResource> resources, Class<Tenant> entityClass) {
        Set<Tenant> tenants = new HashSet<Tenant>();
        if(resources != null) {
            Iterator<TenantResource> iterator = resources.iterator();
            if(iterator.hasNext()) {
                TenantResource groupResource = iterator.next();
                tenants.add(toEntity(groupResource, Tenant.class));
            }
        }
        return tenants;
    }
}


