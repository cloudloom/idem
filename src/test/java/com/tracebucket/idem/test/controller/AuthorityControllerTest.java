package com.tracebucket.idem.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.idem.IdemStarter;
import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.test.config.AccessTokenReceiverConfig;
import com.tracebucket.idem.test.fixture.AuthorityResourceFixture;
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

import java.util.UUID;

/**
 * Created by sadath on 13-May-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IdemStarter.class, AccessTokenReceiverConfig.class})
@WebIntegrationTest
public class AuthorityControllerTest {
    private static final Logger log = LoggerFactory.getLogger(ClientControllerTest.class);

    private RestTemplate restTemplate = null;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccessTokenReceiverConfig accessTokenReceiver;

    private AuthorityResource authority = null;

    private String accessToken = null;

    @Before
    public void setUp() {
        accessToken = accessTokenReceiver.receive("idem-admin", "idem-admin-secret", "admin", "admin");
        restTemplate = new RestTemplate();
    }

    private void createAuthority() throws Exception{
        authority = AuthorityResourceFixture.tempAuthority();
        log.info("Create Authority : " + objectMapper.writeValueAsString(authority));
        ResponseEntity<AuthorityResource> responseEntity = restTemplate.exchange(basePath + "/admin/authority", HttpMethod.POST, RestRequestBuilder.build(authority, accessToken), AuthorityResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getBody());
        authority = responseEntity.getBody();
        log.info("Created Authority : " + objectMapper.writeValueAsString(authority));
    }

    @Test
    public void testCreate() throws Exception {
        createAuthority();
        Assert.assertNotNull(authority.getUid());
    }

    @Test
    public void testUpdate() throws Exception {
        createAuthority();
        String role = "UPDATED_ROLE " + UUID.randomUUID();
        authority.setRole(role);
        ResponseEntity<AuthorityResource> responseEntity = restTemplate.exchange(basePath + "/admin/authority", HttpMethod.PUT, RestRequestBuilder.build(authority, accessToken), AuthorityResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getBody());
        authority = responseEntity.getBody();
        Assert.assertEquals(role, responseEntity.getBody().getRole());
    }

    @Test
    public void testFindOne() throws Exception {
        createAuthority();
        ResponseEntity<AuthorityResource> responseEntity = restTemplate.exchange(basePath + "/admin/authority/" + authority.getUid(), HttpMethod.GET, RestRequestBuilder.build(accessToken), AuthorityResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getBody());
        authority = responseEntity.getBody();
    }

    @After
    public void tearDown() throws Exception {
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