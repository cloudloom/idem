package com.tracebucket.idem.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.idem.IdemStarter;
import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.rest.resource.GroupResource;
import com.tracebucket.idem.rest.resource.UserResource;
import com.tracebucket.idem.test.config.AccessTokenReceiverConfig;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

/**
 * Created by sadath on 30-Apr-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IdemStarter.class, AccessTokenReceiverConfig.class})
@WebIntegrationTest
public class GroupControllerTest {
    private static final Logger log = LoggerFactory.getLogger(GroupControllerTest.class);

    private RestTemplate restTemplate = null;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccessTokenReceiverConfig accessTokenReceiver;

    private String accessToken = null;

    private GroupResource group = null;

    private UserResource user = null;

    private AuthorityResource authority = null;

    @Before
    public void setUp() {
        accessToken = accessTokenReceiver.receive("idem-admin", "idem-admin-secret", "admin", "admin");
        restTemplate = new RestTemplate();
    }

    private void createGroup() throws Exception{
        group = GroupResourceFixture.standardGroup();
        log.info("Create Group : " + objectMapper.writeValueAsString(group));
        group = restTemplate.postForObject(basePath+"/group", group, GroupResource.class);
        log.info("Created Group : " + objectMapper.writeValueAsString(group));
        Assert.assertNotNull(group);
    }

    @Test
    public void testCreateGroup() throws Exception {
        createGroup();
        Assert.assertNotNull(group.getUid());
    }

    @Test
    public void testFindGroupByName() throws Exception {
        createGroup();
        group = restTemplate.getForObject(basePath + "/group/" + group.getName(), GroupResource.class);
        Assert.assertNotNull(group);
        Assert.assertNotNull(group.getUid());
    }

    @Test
    public void testFindAllGroups() throws Exception {
        createGroup();
        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(basePath + "/groups/", String[].class);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertTrue(responseEntity.getBody().length > 0);
    }

    @Test
    public void testRenameGroup() throws Exception {
        createGroup();
        String newGroupName = UUID.randomUUID().toString();
        restTemplate.put(basePath + "/group/" + group.getName() + "/" + newGroupName, GroupResource.class);
        group = restTemplate.getForObject(basePath + "/group/" + newGroupName, GroupResource.class);
        Assert.assertNotNull(group);
        Assert.assertNotNull(group.getUid());
    }

    @Test
    public void testFindUsersInGroup() throws Exception {
        createGroup();
        user = UserResourceFixture.standardUser();
        user = restTemplate.postForObject(basePath+"/user", user, UserResource.class);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUid());
        restTemplate.put(basePath + "/user/" + user.getUsername() + "/group/" + group.getName(), UserResource.class);
        user = restTemplate.getForObject(basePath + "/user/" + user.getUsername(), UserResource.class);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUid());
        Assert.assertNotNull(user.getGroups());
        Assert.assertEquals(1, user.getGroups().size());
        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(basePath + "/group/" + group.getName() + "/users", String[].class);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(1, responseEntity.getBody().length);
    }

    @Test
    public void testAddGroupAuthority() throws Exception {
        createGroup();
        authority = restTemplate.postForObject(basePath + "/authority", AuthorityResourceFixture.tempAuthority(), AuthorityResource.class);
        restTemplate.put(basePath+"/group/" + group.getName(), authority);
        group = restTemplate.getForObject(basePath + "/group/" + group.getName(), GroupResource.class);
        Assert.assertNotNull(group);
        Assert.assertNotNull(group.getUid());
        Assert.assertNotNull(group.getAuthorities());
        Assert.assertEquals(1, group.getAuthorities().size());
    }

    @Test
    public void testFindGroupAuthorities() throws Exception {
        createGroup();
        authority = restTemplate.postForObject(basePath + "/authority", AuthorityResourceFixture.tempAuthority(), AuthorityResource.class);
        restTemplate.put(basePath+"/group/" + group.getName(), authority);
        group = restTemplate.getForObject(basePath + "/group/" + group.getName(), GroupResource.class);
        Assert.assertNotNull(group);
        Assert.assertNotNull(group.getUid());
        ResponseEntity<AuthorityResource[]> responseEntity = restTemplate.getForEntity(basePath + "/group/" + group.getName() + "/authorities", AuthorityResource[].class);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertTrue(responseEntity.getBody().length > 0);
    }

    @Test
    public void testRemoveGroupAuthority() throws Exception {
        createGroup();
        authority = restTemplate.postForObject(basePath + "/authority", AuthorityResourceFixture.tempAuthority(), AuthorityResource.class);
        restTemplate.put(basePath+"/group/" + group.getName(), authority);
        group = restTemplate.getForObject(basePath + "/group/" + group.getName(), GroupResource.class);
        Assert.assertNotNull(group);
        Assert.assertNotNull(group.getUid());
        Assert.assertNotNull(group.getAuthorities());
        Assert.assertEquals(1, group.getAuthorities().size());
        AuthorityResource authorityResourceDelete = null;
        for(AuthorityResource authorityResource1 : group.getAuthorities()) {
            authorityResourceDelete = authorityResource1;
            break;
        }
        restTemplate.put(basePath + "/group/" + group.getName() + "/authority", authorityResourceDelete);
        group = restTemplate.getForObject(basePath + "/group/" + group.getName(), GroupResource.class);
        Assert.assertNotNull(group);
        Assert.assertNotNull(group.getUid());
        Assert.assertNotNull(group.getAuthorities());
        Assert.assertEquals(0, group.getAuthorities().size());
    }

    @After
    public void tearDown() throws Exception{
        if(group != null && group.getUid() != null) {
            restTemplate.delete(basePath + "/group/" + group.getName());
            try {
                restTemplate.getForEntity(new URI(basePath + "/group/" + group.getName()), GroupResource.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                Assert.assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
            }
        }
        if(user != null && user.getUid() != null) {
            restTemplate.delete(basePath + "/user/" + user.getUsername());
            try {
                restTemplate.getForEntity(new URI(basePath + "/user/" + user.getUsername()), UserResource.class);
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