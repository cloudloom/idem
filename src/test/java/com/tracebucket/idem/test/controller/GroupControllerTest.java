package com.tracebucket.idem.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.idem.IdemStarter;
import com.tracebucket.idem.domain.Group;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.rest.resource.GroupResource;
import com.tracebucket.idem.rest.resource.UserResource;
import com.tracebucket.idem.test.config.AccessTokenReceiverConfig;
import com.tracebucket.idem.test.fixture.AuthorityResourceFixture;
import com.tracebucket.idem.test.fixture.GroupResourceFixture;
import com.tracebucket.idem.test.fixture.UserResourceFixture;
import com.tracebucket.idem.test.util.RestRequestBuilder;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
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

    private GroupResource group;

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
        ResponseEntity<GroupResource> responseEntity = restTemplate.exchange(basePath + "/admin/group", HttpMethod.POST, RestRequestBuilder.build(group, accessToken), GroupResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(group = responseEntity.getBody());
        log.info("Created Group : " + objectMapper.writeValueAsString(group));
    }

    @Test
    public void testCreateGroup() throws Exception {
        createGroup();
        Assert.assertNotNull(group.getUid());
    }

    @Test
    public void testFindGroupByName() throws Exception {
        createGroup();
        ResponseEntity<GroupResource> responseEntity = restTemplate.exchange(basePath + "/admin/group/"+group.getName(), HttpMethod.GET, RestRequestBuilder.build(group, accessToken), GroupResource.class);
        Assert.assertNotNull(group = responseEntity.getBody());
        Assert.assertNotNull(group.getUid());
    }

    @Test
    public void testFindAllGroups() throws Exception {
        createGroup();
        ResponseEntity<String[]> responseEntity = restTemplate.exchange(basePath + "/admin/groups", HttpMethod.GET, RestRequestBuilder.build(group, accessToken), String[].class);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertTrue(responseEntity.getBody().length > 0);
    }

    @Test
    public void testRenameGroup() throws Exception {
        createGroup();
        String newGroupName = UUID.randomUUID().toString();
        ResponseEntity<GroupResource> responseEntity = restTemplate.exchange(basePath + "/admin/group/" + group.getName() + "/" + newGroupName, HttpMethod.PUT, RestRequestBuilder.build(accessToken), GroupResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(group = responseEntity.getBody());
        Assert.assertEquals(newGroupName, group.getName());
    }

    @Test
    public void testFindUsersInGroup() throws Exception {
        createGroup();

        user = UserResourceFixture.standardUser();

        ResponseEntity<UserResource> userResponseEntity = restTemplate.exchange(basePath + "/admin/user", HttpMethod.POST, RestRequestBuilder.build(user, accessToken), UserResource.class);
        Assert.assertNotNull(userResponseEntity);
        Assert.assertNotNull(user = userResponseEntity.getBody());
        Assert.assertNotNull(user.getUid());

        ResponseEntity<UserResource> userResponseEntity1 = restTemplate.exchange(basePath + "/admin/user/" + user.getUsername() + "/group/" + group.getName(), HttpMethod.PUT, RestRequestBuilder.build(user, accessToken), UserResource.class);
        Assert.assertNotNull(userResponseEntity1);
        Assert.assertNotNull(user = userResponseEntity1.getBody());
        Assert.assertNotNull(user.getUid());
        Assert.assertNotNull(user.getGroups());
        Assert.assertEquals(1, user.getGroups().size());

        ResponseEntity<String[]> responseEntity = restTemplate.exchange(basePath + "/admin/group/"+group.getName()+"/users", HttpMethod.GET, RestRequestBuilder.build(group, accessToken), String[].class);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(1, responseEntity.getBody().length);
    }

    @Test
    public void testAddGroupAuthority() throws Exception {
        createGroup();
        authority = AuthorityResourceFixture.tempAuthority();
        ResponseEntity<AuthorityResource> authorityResponseEntity = restTemplate.exchange(basePath + "/admin/authority", HttpMethod.POST, RestRequestBuilder.build(authority, accessToken), AuthorityResource.class);
        Assert.assertNotNull(authorityResponseEntity);
        Assert.assertNotNull(authority = authorityResponseEntity.getBody());
        ResponseEntity<GroupResource> groupResponseEntity = restTemplate.exchange(basePath + "/admin/group/" + group.getName(), HttpMethod.PUT, RestRequestBuilder.build(authority, accessToken), GroupResource.class);
        Assert.assertNotNull(groupResponseEntity);
        Assert.assertNotNull(group = groupResponseEntity.getBody());
        Assert.assertNotNull(group.getAuthorities());
        Assert.assertEquals(1, group.getAuthorities().size());
    }

    @Test
    public void testFindGroupAuthorities() throws Exception {
        createGroup();

        authority = AuthorityResourceFixture.tempAuthority();
        ResponseEntity<AuthorityResource> authorityResponseEntity = restTemplate.exchange(basePath + "/admin/authority", HttpMethod.POST, RestRequestBuilder.build(authority, accessToken), AuthorityResource.class);
        Assert.assertNotNull(authorityResponseEntity);
        Assert.assertNotNull(authority = authorityResponseEntity.getBody());

        ResponseEntity<GroupResource> groupResponseEntity = restTemplate.exchange(basePath + "/admin/group/" + group.getName(), HttpMethod.PUT, RestRequestBuilder.build(authority, accessToken), GroupResource.class);

        Assert.assertNotNull(groupResponseEntity);
        Assert.assertNotNull(group.getUid());

        ResponseEntity<AuthorityResource[]> responseEntity = restTemplate.exchange(basePath + "/admin/group/" + group.getName() + "/authorities", HttpMethod.GET, RestRequestBuilder.build(authority, accessToken), AuthorityResource[].class);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertTrue(responseEntity.getBody().length > 0);
    }

    @Test
    public void testRemoveGroupAuthority() throws Exception {
        createGroup();

        authority = AuthorityResourceFixture.tempAuthority();
        ResponseEntity<AuthorityResource> authorityResponseEntity = restTemplate.exchange(basePath + "/admin/authority", HttpMethod.POST, RestRequestBuilder.build(authority, accessToken), AuthorityResource.class);
        Assert.assertNotNull(authorityResponseEntity);
        Assert.assertNotNull(authority = authorityResponseEntity.getBody());

        ResponseEntity<GroupResource> groupResponseEntity = restTemplate.exchange(basePath + "/admin/group/" + group.getName(), HttpMethod.PUT, RestRequestBuilder.build(authority, accessToken), GroupResource.class);

        Assert.assertNotNull(groupResponseEntity);
        Assert.assertNotNull(group = groupResponseEntity.getBody());
        Assert.assertNotNull(group.getUid());
        Assert.assertNotNull(group.getAuthorities());
        Assert.assertEquals(1, group.getAuthorities().size());

        AuthorityResource authorityResourceDelete = null;
        for(AuthorityResource authorityResource1 : group.getAuthorities()) {
            authorityResourceDelete = authorityResource1;
            break;
        }
        ResponseEntity<GroupResource> groupResponseEntity2 = restTemplate.exchange(basePath + "/admin/group/" + group.getName() + "/authority", HttpMethod.PUT, RestRequestBuilder.build(authorityResourceDelete, accessToken), GroupResource.class);
        Assert.assertNotNull(groupResponseEntity2);
        Assert.assertNotNull(group = groupResponseEntity2.getBody());
        Assert.assertNotNull(group.getAuthorities());
        Assert.assertEquals(0, group.getAuthorities().size());
    }

    @After
    public void tearDown() throws Exception{
        if(group != null && group.getUid() != null) {
            ResponseEntity<Boolean> responseEntity = restTemplate.exchange(basePath + "/admin/group/" + group.getName(), HttpMethod.DELETE, RestRequestBuilder.build(accessToken), Boolean.class);
            Assert.assertNotNull(responseEntity);
            Assert.assertTrue(responseEntity.getBody());
            try {
                restTemplate.exchange(basePath + "/admin/group/" + group.getName(), HttpMethod.GET, RestRequestBuilder.build(accessToken), GroupResource.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                Assert.assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
            }
        }
        if(user != null && user.getUid() != null) {
            ResponseEntity<Boolean> responseEntity = restTemplate.exchange(basePath + "/admin/user/" + user.getUsername(), HttpMethod.DELETE, RestRequestBuilder.build(accessToken), Boolean.class);
            Assert.assertNotNull(responseEntity);
            Assert.assertTrue(responseEntity.getBody());
            try {
                restTemplate.exchange(basePath + "/admin/user/" + user.getUsername(), HttpMethod.GET, RestRequestBuilder.build(accessToken), UserResource.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                Assert.assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
            }
        }
        if (authority != null && authority.getUid() != null) {
            ResponseEntity<Boolean> responseEntity = restTemplate.exchange(basePath + "/admin/authority/" + authority.getUid(), HttpMethod.DELETE, RestRequestBuilder.build(accessToken), Boolean.class);
            Assert.assertNotNull(responseEntity);
            Assert.assertTrue(responseEntity.getBody());
            try {
                restTemplate.exchange(basePath + "/admin/authority/" + authority.getUid(), HttpMethod.GET, RestRequestBuilder.build(accessToken), AuthorityResource.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                Assert.assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
            }
        }
    }
}