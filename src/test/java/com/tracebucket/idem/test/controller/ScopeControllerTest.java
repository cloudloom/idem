package com.tracebucket.idem.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.idem.IdemStarter;
import com.tracebucket.idem.rest.resource.ScopeResource;
import com.tracebucket.idem.test.config.AccessTokenReceiverConfig;
import com.tracebucket.idem.test.fixture.ScopeResourceFixture;
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

/**
 * Created by Vishwajit on 04-08-2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IdemStarter.class, AccessTokenReceiverConfig.class})
@WebIntegrationTest
public class ScopeControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ScopeControllerTest.class);

    private RestTemplate restTemplate = null;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccessTokenReceiverConfig accessTokenReceiver;

    private String accessToken = null;

    private ScopeResource scope;

    @Before
    public void setUp() {
        accessToken = accessTokenReceiver.receive("idem-admin", "idem-admin-secret", "admin", "admin");
        restTemplate = new RestTemplate();
    }

    private void createScope() throws Exception{
        scope = ScopeResourceFixture.standardScopeResource();
        log.info("Create Scope : " + objectMapper.writeValueAsString(scope));
        ResponseEntity<ScopeResource> responseEntity = restTemplate.exchange(basePath + "/admin/scope", HttpMethod.POST, RestRequestBuilder.build(scope, accessToken), ScopeResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(scope = responseEntity.getBody());
        log.info("Created Scope : " + objectMapper.writeValueAsString(scope));
    }

    @Test
    public void testCreate() throws Exception {
        createScope();
        Assert.assertNotNull(scope.getUid());
    }

    @Test
    public void testUpdate() throws Exception {
        createScope();
        scope.setName("Updated_Name");
        ResponseEntity<ScopeResource> responseEntity = restTemplate.exchange(basePath + "/admin/scope/update", HttpMethod.PUT, RestRequestBuilder.build(scope, accessToken), ScopeResource.class);

        Assert.assertNotNull(scope.getUid());
        Assert.assertEquals("Updated_Name", responseEntity.getBody().getName());
    }

    @Test
    public void testFindOne() throws Exception {
        createScope();
        ResponseEntity<ScopeResource> responseEntity = restTemplate.exchange(basePath + "/admin/scope/"+scope.getUid(), HttpMethod.GET, RestRequestBuilder.build(scope, accessToken), ScopeResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void testFindByName() throws Exception {
        createScope();
        ResponseEntity<ScopeResource> responseEntity = restTemplate.exchange(basePath + "/admin/scopes/"+scope.getName(), HttpMethod.GET, RestRequestBuilder.build(scope, accessToken), ScopeResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertNotNull(responseEntity.getBody().getName());
    }

    @Test
    public void testDeleteByName() throws Exception {
        createScope();
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(basePath + "/admin/scope/delete/"+scope.getName(), HttpMethod.DELETE, RestRequestBuilder.build(scope, accessToken), Boolean.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertTrue(responseEntity.getBody());
        scope = null;
    }

    @Test
    public void testDelete() throws Exception {
        createScope();
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(basePath + "/admin/scope/"+scope.getUid(), HttpMethod.DELETE, RestRequestBuilder.build(scope, accessToken), Boolean.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertTrue(responseEntity.getBody());
        scope = null;
    }

    @After
    public void tearDown() throws Exception {
        if (scope != null && scope.getUid() != null) {
            ResponseEntity<Boolean> responseEntity = restTemplate.exchange(basePath + "/admin/scope/delete/" + scope.getName(), HttpMethod.DELETE, RestRequestBuilder.build(accessToken), Boolean.class);
            Assert.assertNotNull(responseEntity);
            Assert.assertTrue(responseEntity.getBody());
            try {
                restTemplate.exchange(basePath + "/admin/scopes/" + scope.getName(), HttpMethod.GET, RestRequestBuilder.build(accessToken), ScopeResource.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                Assert.assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
            }
        }
    }
}
