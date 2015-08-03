package com.tracebucket.idem.rest.assembler.resource;

import com.tracebucket.idem.domain.Tenant;
import com.tracebucket.idem.rest.resource.TenantResource;
import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.ResourceAssembler;
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
public class TenantResourceAssembler extends ResourceAssembler<TenantResource, Tenant> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public TenantResource toResource(Tenant entity, Class<TenantResource> resourceClass) {
        TenantResource tenant = null;
        try {
            tenant = resourceClass.newInstance();
            if (entity != null) {
                tenant.setUid(entity.getEntityId().getId());
                tenant.setPassive(entity.isPassive());
                tenant.setDescription(entity.getDescription());
                tenant.setLogo(entity.getLogo());
                tenant.setName(entity.getName());
            }
        } catch (InstantiationException ie) {

        } catch (IllegalAccessException iae) {

        }
        return tenant;
    }


    @Override
    public Set<TenantResource> toResources(Collection<Tenant> entities, Class<TenantResource> resourceClass) {
        Set<TenantResource> tenants = new HashSet<TenantResource>();
        if(entities != null && entities.size() > 0) {
            Iterator<Tenant> iterator = entities.iterator();
            if(iterator.hasNext()) {
                Tenant tenant = iterator.next();
                tenants.add(toResource(tenant, TenantResource.class));
            }
        }
        return tenants;
    }
}
