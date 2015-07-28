package com.tracebucket.idem.rest.controller;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.service.impl.AuthorityServiceImpl;
import com.tracebucket.tron.assembler.AssemblerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by sadath on 13-May-15.
 */
@RestController(value = "idemAuthorityController")
@RequestMapping("/admin")
public class AuthorityController {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Autowired
    private AuthorityServiceImpl authorityServiceImpl;

    @RequestMapping(value = "/authority", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorityResource> save(@RequestBody AuthorityResource authorityResource) {
        Authority authority = assemblerResolver.resolveEntityAssembler(Authority.class, AuthorityResource.class).toEntity(authorityResource, Authority.class);
        authority = authorityServiceImpl.save(authority);
        if(authority != null) {
            authorityResource = assemblerResolver.resolveResourceAssembler(AuthorityResource.class, Authority.class).toResource(authority, AuthorityResource.class);
            return new ResponseEntity<AuthorityResource>(authorityResource, HttpStatus.OK);
        }
        return new ResponseEntity<AuthorityResource>(new AuthorityResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/authority", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorityResource> update(@RequestBody AuthorityResource authorityResource) {
        Authority authority = assemblerResolver.resolveEntityAssembler(Authority.class, AuthorityResource.class).toEntity(authorityResource, Authority.class);
        authority = authorityServiceImpl.save(authority);
        if(authority != null) {
            authorityResource = assemblerResolver.resolveResourceAssembler(AuthorityResource.class, Authority.class).toResource(authority, AuthorityResource.class);
            return new ResponseEntity<AuthorityResource>(authorityResource, HttpStatus.OK);
        }
        return new ResponseEntity<AuthorityResource>(new AuthorityResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/authority/{uid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorityResource> findOne(@PathVariable("uid") String uid) {
        Authority authority = authorityServiceImpl.findOne(uid);
        if(authority != null) {
            AuthorityResource authorityResource = assemblerResolver.resolveResourceAssembler(AuthorityResource.class, Authority.class).toResource(authority, AuthorityResource.class);
            return new ResponseEntity<AuthorityResource>(authorityResource, HttpStatus.OK);
        }
        return new ResponseEntity<AuthorityResource>(new AuthorityResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/authorities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<AuthorityResource>> findAll() {
        List<Authority> authorities = authorityServiceImpl.findAll();
        if(authorities != null && authorities.size() > 0) {
            Set<AuthorityResource> authorityResources = assemblerResolver.resolveResourceAssembler(AuthorityResource.class, Authority.class).toResources(authorities, AuthorityResource.class);
            return new ResponseEntity<Set<AuthorityResource>>(authorityResources, HttpStatus.OK);
        }
        return new ResponseEntity<Set<AuthorityResource>>(Collections.emptySet(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/authorities/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> findAllAuthorities() {
        List<Authority> authorities = authorityServiceImpl.findAll();
        if(authorities != null && authorities.size() > 0) {
            final Set<String> set = new HashSet<String>();
            authorities.stream().forEach(authority -> {
                set.add(authority.getRole());
            });
            return new ResponseEntity<Set<String>>(set, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/authority/{uid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> delete(@PathVariable("uid") String uid) {
        return new ResponseEntity<Boolean>(authorityServiceImpl.delete(uid), HttpStatus.OK);
    }

    @RequestMapping(value = "/authorities", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteAll() {
        return new ResponseEntity<Boolean>(authorityServiceImpl.deleteAll(), HttpStatus.OK);
    }
}