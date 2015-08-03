package com.tracebucket.idem.rest.controller;

import com.tracebucket.idem.domain.Tenant;
import com.tracebucket.idem.rest.resource.TenantResource;
import com.tracebucket.idem.service.impl.TenantServiceImpl;
import com.tracebucket.tron.assembler.AssemblerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishwajit on 03-08-2015.
 */
@RestController(value = "idemTenantController")
@RequestMapping("/admin")
public class TenantController {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Autowired
    private TenantServiceImpl tenantServiceImpl;


    @RequestMapping(value = "/tenant", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TenantResource> createTenant(@RequestBody TenantResource tenantResource) {
        Tenant tenant = assemblerResolver.resolveEntityAssembler(Tenant.class, TenantResource.class).toEntity(tenantResource, Tenant.class);

        tenant = tenantServiceImpl.save(tenant);

        if(tenant != null) {
            tenantResource = assemblerResolver.resolveResourceAssembler(TenantResource.class, Tenant.class).toResource(tenant, TenantResource.class);
            return new ResponseEntity<TenantResource>(tenantResource, HttpStatus.OK);
        }
        return new ResponseEntity<TenantResource>(new TenantResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/tenant/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TenantResource> updateTenant(@RequestBody TenantResource tenantResource) {
        Tenant tenant = assemblerResolver.resolveEntityAssembler(Tenant.class, TenantResource.class).toEntity(tenantResource, Tenant.class);

        tenant = tenantServiceImpl.update(tenant);

        if(tenant != null) {
            tenantResource = assemblerResolver.resolveResourceAssembler(TenantResource.class, Tenant.class).toResource(tenant, TenantResource.class);
            return new ResponseEntity<TenantResource>(tenantResource, HttpStatus.OK);
        }
        return new ResponseEntity<TenantResource>(new TenantResource(), HttpStatus.NOT_ACCEPTABLE);
    }
}
