package com.tracebucket.idem.rest.controller;

import com.tracebucket.idem.domain.Scope;
import com.tracebucket.idem.rest.resource.ScopeResource;
import com.tracebucket.idem.service.impl.ScopeServiceImpl;
import com.tracebucket.tron.assembler.AssemblerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Vishwajit on 04-08-2015.
 */
@RestController(value = "idemScopeController")
@RequestMapping("/admin")
public class ScopeController {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Autowired
    private ScopeServiceImpl scopeServiceImpl;


    @RequestMapping(value = "/scope", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScopeResource> createScope(@RequestBody ScopeResource scopeResource) {
        Scope scope = assemblerResolver.resolveEntityAssembler(Scope.class, ScopeResource.class).toEntity(scopeResource, Scope.class);

        scope = scopeServiceImpl.save(scope);

        if(scope != null) {
            scopeResource = assemblerResolver.resolveResourceAssembler(ScopeResource.class, Scope.class).toResource(scope, ScopeResource.class);
            return new ResponseEntity<ScopeResource>(scopeResource, HttpStatus.OK);
        }
        return new ResponseEntity<ScopeResource>( HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/scope/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScopeResource> updateScope(@RequestBody ScopeResource scopeResource) {
        Scope scope = assemblerResolver.resolveEntityAssembler(Scope.class, ScopeResource.class).toEntity(scopeResource, Scope.class);

        scope = scopeServiceImpl.update(scope);

        if(scope != null) {
            scopeResource = assemblerResolver.resolveResourceAssembler(ScopeResource.class, Scope.class).toResource(scope, ScopeResource.class);
            return new ResponseEntity<ScopeResource>(scopeResource, HttpStatus.OK);
        }
        return new ResponseEntity<ScopeResource>(HttpStatus.NOT_ACCEPTABLE);
    }


    @RequestMapping(value = "/scope/{scopeUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScopeResource> findOne(HttpServletRequest request, @PathVariable("scopeUid") String scopeUid) {

        Scope scope = scopeServiceImpl.findOne(scopeUid);
        if(scope != null) {
            ScopeResource scopeResource = assemblerResolver.resolveResourceAssembler(ScopeResource.class, Scope.class).toResource(scope, ScopeResource.class);
            return new ResponseEntity<ScopeResource>(scopeResource, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/scopes/{scopeName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScopeResource> findByName(@PathVariable("scopeName") String scopeName) {
        Scope scope = scopeServiceImpl.findByName(scopeName);
        if(scope != null) {
            ScopeResource scopeResource = assemblerResolver.resolveResourceAssembler(ScopeResource.class, Scope.class).toResource(scope, ScopeResource.class);
            return new ResponseEntity<ScopeResource>(scopeResource, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/scope/delete/{scopeByName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteByName(@PathVariable("scopeByName") String scopeName) {
        return new ResponseEntity<Boolean>(scopeServiceImpl.deleteByName(scopeName), HttpStatus.OK);
    }


    @RequestMapping(value = "/scope/{scopeUid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> delete(@PathVariable("scopeUid") String scopeUid) {
        return new ResponseEntity<Boolean>(scopeServiceImpl.delete(scopeUid), HttpStatus.OK);
    }

    @RequestMapping(value = "/scope/deleteAll", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteAll() {
        boolean status = scopeServiceImpl.deleteAll();
        if(status) {
            return new ResponseEntity<Boolean>(status, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.NOT_MODIFIED);
    }

}
