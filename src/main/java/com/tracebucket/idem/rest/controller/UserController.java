package com.tracebucket.idem.rest.controller;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.repository.jpa.UserRepository;
import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.rest.resource.UserResource;
import com.tracebucket.idem.service.impl.UserDetailsManagerImpl;
import com.tracebucket.tron.assembler.AssemblerResolver;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by sadath on 29-Apr-15.
 */
@RestController(value = "idemUserController")
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private AssemblerResolver assemblerResolver;

    @Autowired
    private UserDetailsManagerImpl userDetailsManagerImpl;

    @Autowired
    private Mapper mapper;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> createUser(@RequestBody UserResource userResource) {
        User user = assemblerResolver.resolveEntityAssembler(User.class, UserResource.class).toEntity(userResource, User.class);
        userDetailsManagerImpl.createUser(user);
        //user = (User)userDetailsManagerImpl.loadUserByUsername(user.getUsername());
        user = userRepository.findByUsername(user.getUsername());
        if(user != null) {
            userResource = assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResource(user, UserResource.class);
            return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        }
        return new ResponseEntity<UserResource>(new UserResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<UserResource>> createUsers(@RequestBody List<UserResource> userResource) {
        Set<User> users = assemblerResolver.resolveEntityAssembler(User.class, UserResource.class).toEntities(userResource, User.class);
        if(users != null && users.size() > 0) {
            userDetailsManagerImpl.createUsers(users);
            List<User> loadedUsers = new ArrayList<>();
            users.stream().forEach(user -> {
                loadedUsers.add((User)userDetailsManagerImpl.loadUserByUserName(user.getUsername()));
            });
            if (loadedUsers != null && loadedUsers.size() > 0) {
                Set<UserResource> userResourceSet = assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResources(loadedUsers, UserResource.class);
                return new ResponseEntity<Set<UserResource>>(userResourceSet, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> updateUser(@RequestBody UserResource userResource) {
        User user = assemblerResolver.resolveEntityAssembler(User.class, UserResource.class).toEntity(userResource, User.class);
        userDetailsManagerImpl.updateUser(user);
        user = (User)userDetailsManagerImpl.loadUserByUsername(user.getUsername());
        if(user != null) {
            userResource = assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResource(user, UserResource.class);
            return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        }
        return new ResponseEntity<UserResource>(new UserResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user/password", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        userDetailsManagerImpl.changePassword(oldPassword, newPassword);
        User user = (User)userDetailsManagerImpl.getCurrentUser();
        if(user != null) {
            if(user.getPassword().equals(newPassword)) {
                return new ResponseEntity(true, HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @RequestMapping(value = "/user/password/reset", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> resetPassword(@RequestParam("userName") String userName, @RequestParam("newPassword") String newPassword) {
        userDetailsManagerImpl.resetPassword(userName, newPassword);
        User user = (User) userDetailsManagerImpl.loadUserByUserName(userName);
        if (user != null) {
            if(user.getPassword() != null && user.getPassword().equals(newPassword)) {
                user.setPassword(null);
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @RequestMapping(value = "/user/{userName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteUser(@PathVariable("userName") String userName) {
        userDetailsManagerImpl.deleteUser(userName);
        User user = (User)userDetailsManagerImpl.loadUserByUsername(userName);
        return new ResponseEntity<Boolean>(user == null ? true : false, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userName}/exists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> userExists(@PathVariable("userName") String userName) {
        return new ResponseEntity<Boolean>(userDetailsManagerImpl.userExists(userName), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> loadUserByUsername(@PathVariable("userName") String userName) {
        User user = (User)userDetailsManagerImpl.loadUserByUsername(userName);
        if(user != null) {
            UserResource userResource = assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResource(user, UserResource.class);
            return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        }
        return new ResponseEntity<UserResource>(new UserResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/users/userNames", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<UserResource>> loadUserByUsername(@RequestBody List<String> userNames) {
        List<User> users = userDetailsManagerImpl.loadUserByUserNames(userNames);
        if(users != null && users.size() > 0) {
            Set<UserResource> userResources = new HashSet<UserResource>();
            users.stream().forEach(user -> {
                UserResource userResource = new UserResource();
                userResource.setUsername(user.getUsername());
                if(user.getRawAuthorities() != null) {
                    Set<AuthorityResource> authorityResources = new HashSet<AuthorityResource>();
                    for(Authority authority : user.getRawAuthorities()) {
                        AuthorityResource authorityResource = new AuthorityResource();
                        mapper.map(authority, authorityResource);
                        authorityResources.add(authorityResource);
                    }
                    if(authorityResources.size() > 0) {
                        userResource.setAuthorities(authorityResources);
                    }
                }
                userResources.add(userResource);
            });
            return new ResponseEntity<Set<UserResource>>(userResources, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user/{userName}/group/{groupName}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> addUserToGroup(@PathVariable("userName") String userName, @PathVariable("groupName") String groupName) {
        userDetailsManagerImpl.addUserToGroup(userName, groupName);
        User user = (User)userDetailsManagerImpl.loadUserByUsername(userName);
        if(user != null) {
            UserResource userResource = assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResource(user, UserResource.class);
            return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        }
        return new ResponseEntity<UserResource>(new UserResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user/{userName}/group/{groupName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> removeUserFromGroup(@PathVariable("userName") String userName, @PathVariable("groupName") String groupName) {
        userDetailsManagerImpl.removeUserFromGroup(userName, groupName);
        User user = (User)userDetailsManagerImpl.loadUserByUsername(userName);
        if(user != null) {
            UserResource userResource = assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResource(user, UserResource.class);
            return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        }
        return new ResponseEntity<UserResource>(new UserResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/users/authorities/minimal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, String>>> getUsers() {
        List<User> users = userDetailsManagerImpl.findAll();
        if (users != null && users.size() > 0) {
            List<Map<String, String>> userResources = new ArrayList<Map<String, String>>();
            users.stream().forEach(user -> {
                Map<String, String> map = new HashMap<String, String>();
                map.put("username" , user.getUsername());
                final List<String> authorities = new ArrayList<String>();
                if(user.getRawAuthorities() != null && user.getRawAuthorities().size() > 0) {
                    user.getRawAuthorities().stream().forEach(authority -> {
                        authorities.add(authority.getRole());
                    });
                }
                map.put("authorities", authorities.toString());
                userResources.add(map);
            });
            return new ResponseEntity<List<Map<String, String>>>(userResources, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}