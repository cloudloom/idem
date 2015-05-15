package com.tracebucket.idem.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.idem.IdemStarter;
import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.test.config.AccessTokenReceiverConfig;
import com.tracebucket.idem.test.fixture.AuthorityResourceFixture;
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

    @Before
    public void setUp() {
        String token = accessTokenReceiver.receive("idem-admin", "idem-admin-secret", "admin", "admin");
        restTemplate = new RestTemplate();
    }

    private void createAuthority() throws Exception{
        authority = AuthorityResourceFixture.tempAuthority();
        log.info("Create Authority : " + objectMapper.writeValueAsString(authority));
        authority = restTemplate.postForObject(basePath + "/authority", authority, AuthorityResource.class);
        log.info("Created Authority : " + objectMapper.writeValueAsString(authority));
        Assert.assertNotNull(authority);
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
        restTemplate.put(basePath + "/authority", authority);
        authority = restTemplate.getForObject(basePath + "/authority/" + authority.getUid(), AuthorityResource.class);
        Assert.assertNotNull(authority);
        Assert.assertNotNull(authority.getUid());
        Assert.assertEquals(role, authority.getRole());
    }

    @Test
    public void testFindOne() throws Exception {
        createAuthority();
        authority = restTemplate.getForObject(basePath + "/authority/" + authority.getUid(), AuthorityResource.class);
        Assert.assertNotNull(authority);
        Assert.assertNotNull(authority.getUid());
    }

    @After
    public void tearDown() throws Exception {
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