package com.tracebucket.idem.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.idem.IdemStarter;
import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.rest.resource.GroupResource;
import com.tracebucket.idem.rest.resource.UserResource;
import com.tracebucket.idem.test.fixture.AuthorityResourceFixture;
import com.tracebucket.idem.test.fixture.GroupResourceFixture;
import com.tracebucket.idem.test.fixture.UserResourceFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by sadath on 30-Apr-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IdemStarter.class)
@WebIntegrationTest
public class UserControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    private RestTemplate restTemplate = null;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Autowired
    private ObjectMapper objectMapper;

    private UserResource user = null;

    private GroupResource group = null;

    private AuthorityResource authority = null;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    private void createUser() throws Exception {
        user = UserResourceFixture.standardUser();
        log.info("Create User : " + objectMapper.writeValueAsString(user));
        user = restTemplate.postForObject(basePath+"/user", user, UserResource.class);
        log.info("Created User : " + objectMapper.writeValueAsString(user));
        Assert.assertNotNull(user);
    }

    @Test
    public void testCreateUser() throws Exception {
        createUser();
        Assert.assertNotNull(user.getUid());
    }

    @Test
    public void testUpdateUser() throws Exception {
        createUser();
        authority = restTemplate.postForObject(basePath + "/authority", AuthorityResourceFixture.tempAuthority(), AuthorityResource.class);
        user.getAuthorities().add(authority);
        restTemplate.put(basePath + "/user", user);
        user = restTemplate.getForObject(basePath + "/user/" + user.getUsername(), UserResource.class);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUid());
        Assert.assertNotNull(user.getAuthorities());
        Assert.assertEquals(1, user.getAuthorities().size());
    }

/*    @Test
    public void testChangePassword() throws Exception {
        createUser();
        String newPassword = UUID.randomUUID().toString();
        restTemplate.put(basePath + "/user/password?oldPassword="+user.getPassword()+"&newPassword="+newPassword, UserResource.class);
        user = restTemplate.getForObject(basePath + "/user/" + user.getUsername(), UserResource.class);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUid());
        Assert.assertEquals(newPassword, user.getPassword());
    }*/

    @Test
    public void testUserExists() throws Exception {
        createUser();
        Boolean status = restTemplate.getForObject(basePath + "/user/" + user.getUsername() + "/exists", Boolean.class);
        Assert.assertTrue(status);
    }

    @Test
    public void testFindByUsername() throws Exception {
        createUser();
        user = restTemplate.getForObject(basePath + "/user/" + user.getUsername(), UserResource.class);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUid());
    }

    @Test
    public void testAddUserToGroup() throws Exception {
        createUser();
        group = GroupResourceFixture.standardGroup();
        group = restTemplate.postForObject(basePath+"/group", group, GroupResource.class);
        Assert.assertNotNull(group);
        restTemplate.put(basePath + "/user/" + user.getUsername() + "/group/" + group.getName(), UserResource.class);
        user = restTemplate.getForObject(basePath + "/user/" + user.getUsername(), UserResource.class);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUid());
        Assert.assertNotNull(user.getGroups());
        Assert.assertEquals(1, user.getGroups().size());
    }

    @Test
    public void testRemoveUserFromGroup() throws Exception {
        createUser();
        group = GroupResourceFixture.standardGroup();
        group = restTemplate.postForObject(basePath + "/group", group, GroupResource.class);
        Assert.assertNotNull(group);
        restTemplate.put(basePath + "/user/" + user.getUsername() + "/group/" + group.getName(), UserResource.class);
        user = restTemplate.getForObject(basePath + "/user/" + user.getUsername(), UserResource.class);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUid());
        Assert.assertNotNull(user.getGroups());
        Assert.assertEquals(1, user.getGroups().size());
        restTemplate.delete(basePath + "/user/" + user.getUsername() + "/group/" + group.getName());
        user = restTemplate.getForObject(basePath + "/user/" + user.getUsername(), UserResource.class);
        Assert.assertNotNull(user);
        Assert.assertEquals(0, user.getGroups().size());
    }

    @After
    public void tearDown() throws Exception{
        if(user != null && user.getUid() != null) {
            restTemplate.delete(basePath + "/user/" + user.getUsername());
            try {
                restTemplate.getForEntity(new URI(basePath + "/user/" + user.getUsername()), UserResource.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                Assert.assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
            }
        }
        if(group != null && group.getUid() != null) {
            restTemplate.delete(basePath + "/group/" + group.getName());
            try {
                restTemplate.getForEntity(new URI(basePath + "/group/" + group.getName()), GroupResource.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                Assert.assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
            }
        }
        if (authority != null && authority.getUid() != null) {
            restTemplate.delete(basePath + "/authority/" + authority.getUid());
            try {
                restTemplate.getForEntity(new URI(basePath + "/authority/" + authority.getUid()), AuthorityResource.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                Assert.assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
            }
        }
    }
}