package com.tracebucket.idem.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.idem.IdemStarter;
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
import java.util.UUID;

/**
 * Created by sadath on 30-Apr-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IdemStarter.class, AccessTokenReceiverConfig.class})
@WebIntegrationTest
public class UserControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    private RestTemplate restTemplate = null;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccessTokenReceiverConfig accessTokenReceiver;

    private String accessToken = null;

    private UserResource user = null;

    private GroupResource group = null;

    private AuthorityResource authority = null;

    @Before
    public void setUp() {
        accessToken = accessTokenReceiver.receive("idem-admin", "idem-admin-secret", "admin", "admin");
        restTemplate = new RestTemplate();
    }

    private void createUser() throws Exception {
        user = UserResourceFixture.standardUser();
        log.info("Create User : " + objectMapper.writeValueAsString(user));
        ResponseEntity<UserResource> responseEntity = restTemplate.exchange(basePath + "/admin/user", HttpMethod.POST, RestRequestBuilder.build(user, accessToken), UserResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getBody());
        user = responseEntity.getBody();
        log.info("Created Group : " + objectMapper.writeValueAsString(group));
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

        authority = AuthorityResourceFixture.tempAuthority();
        ResponseEntity<AuthorityResource> authorityResponseEntity = restTemplate.exchange(basePath + "/admin/authority", HttpMethod.POST, RestRequestBuilder.build(authority, accessToken), AuthorityResource.class);
        Assert.assertNotNull(authorityResponseEntity);
        Assert.assertNotNull(authority = authorityResponseEntity.getBody());

        user.getAuthorities().add(authority);

        ResponseEntity<UserResource> responseEntity = restTemplate.exchange(basePath + "/admin/user", HttpMethod.PUT, RestRequestBuilder.build(user, accessToken), UserResource.class);

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(user = responseEntity.getBody());

        Assert.assertEquals(1, user.getAuthorities().size());
    }

/*    @Test
    public void testChangePassword() throws Exception {
        createUser();
        accessToken = accessTokenReceiver.receive("idem-admin", "idem-admin-secret", user.getUsername(), user.getPassword());
        String newPassword = UUID.randomUUID().toString();
        ResponseEntity<UserResource> responseEntity = restTemplate.exchange(basePath + "/admin/user/password?oldPassword="+user.getPassword()+"&newPassword="+newPassword,HttpMethod.PUT, RestRequestBuilder.build(accessToken), UserResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(user = responseEntity.getBody());
        Assert.assertEquals(newPassword, user.getPassword());
    }*/

    @Test
    public void testUserExists() throws Exception {
        createUser();
        ResponseEntity<Boolean> status = restTemplate.exchange(basePath + "/admin/user/" + user.getUsername() + "/exists", HttpMethod.GET, RestRequestBuilder.build(user, accessToken), Boolean.class);
        Assert.assertTrue(status.getBody().booleanValue());
    }

    @Test
    public void testFindByUsername() throws Exception {
        createUser();
        ResponseEntity<UserResource> responseEntity = restTemplate.exchange(basePath + "/admin/user/" + user.getUsername(),HttpMethod.GET, RestRequestBuilder.build(user, accessToken), UserResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(user = responseEntity.getBody());
        Assert.assertNotNull(user.getUsername());
        Assert.assertNotNull(user.getUid());
    }

    @Test
    public void testAddUserToGroup() throws Exception {
        createUser();
        group = GroupResourceFixture.standardGroup();
        ResponseEntity<GroupResource> groupResponseEntity = restTemplate.exchange(basePath+"/admin/group", HttpMethod.POST, RestRequestBuilder.build(group, accessToken), GroupResource.class);
        Assert.assertNotNull(groupResponseEntity);
        Assert.assertNotNull(groupResponseEntity.getBody().getName());


        restTemplate.exchange(basePath + "/admin/user/" + user.getUsername() + "/group/" + group.getName(), HttpMethod.PUT, RestRequestBuilder.build(user, accessToken), UserResource.class);
        ResponseEntity<UserResource> responseEntity = restTemplate.exchange(basePath + "/admin/user/" + user.getUsername(), HttpMethod.GET, RestRequestBuilder.build(user, accessToken), UserResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(user = responseEntity.getBody());
        Assert.assertNotNull(user.getUid());
        Assert.assertNotNull(user.getGroups());
        Assert.assertEquals(1, user.getGroups().size());
    }

    @Test
    public void testRemoveUserFromGroup() throws Exception {
        createUser();
        group = GroupResourceFixture.standardGroup();
        ResponseEntity<GroupResource> groupResponseEntity = restTemplate.exchange(basePath+"/admin/group", HttpMethod.POST, RestRequestBuilder.build(group, accessToken), GroupResource.class);
        Assert.assertNotNull(groupResponseEntity);
        Assert.assertNotNull(group = groupResponseEntity.getBody());

        ResponseEntity<UserResource> responseEntity = restTemplate.exchange(basePath + "/admin/user/" + user.getUsername() + "/group/" + group.getName(), HttpMethod.PUT, RestRequestBuilder.build(user, accessToken), UserResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(user = responseEntity.getBody());
        Assert.assertNotNull(user.getUid());
        Assert.assertNotNull(user.getGroups());
        Assert.assertEquals(1, user.getGroups().size());

        ResponseEntity<UserResource> responseEntity1 = restTemplate.exchange(basePath + "/admin/user/" + user.getUsername() + "/group/" + group.getName(), HttpMethod.DELETE, RestRequestBuilder.build(user, accessToken), UserResource.class);
        Assert.assertNotNull(responseEntity1);
        Assert.assertNotNull(responseEntity1.getBody());
        Assert.assertEquals(0, responseEntity1.getBody().getGroups().size());
    }

    @After
    public void tearDown() throws Exception{
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