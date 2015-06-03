package com.tracebucket.idem.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.idem.IdemStarter;
import com.tracebucket.idem.rest.resource.ClientResource;
import com.tracebucket.idem.test.config.AccessTokenReceiverConfig;
import com.tracebucket.idem.test.fixture.ClientResourceFixture;
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
 * Created by sadath on 30-Apr-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IdemStarter.class, AccessTokenReceiverConfig.class})
@WebIntegrationTest
public class ClientControllerTest {
    private static final Logger log = LoggerFactory.getLogger(ClientControllerTest.class);

    private RestTemplate restTemplate = null;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccessTokenReceiverConfig accessTokenReceiver;

    private String accessToken = null;

    private ClientResource client = null;

    @Before
    public void setUp() {
        accessToken = accessTokenReceiver.receive("idem-admin", "idem-admin-secret", "admin", "admin");
        restTemplate = new RestTemplate();
    }

    private void createClient() throws Exception{
        client = ClientResourceFixture.tempClient();
        log.info("Create Client : " + objectMapper.writeValueAsString(client));
        ResponseEntity<ClientResource> responseEntity = restTemplate.exchange(basePath + "/admin/client", HttpMethod.POST, RestRequestBuilder.build(client, accessToken), ClientResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(        client = responseEntity.getBody());
        log.info("Created Client : " + objectMapper.writeValueAsString(client));
    }

    @Test
    public void testCreate() throws Exception {
        createClient();
        Assert.assertNotNull(client.getClientId());
    }

    @Test
    public void testUpdate() throws Exception {
        createClient();
        Assert.assertNotNull(client);
        Assert.assertNotNull(client.getUid());
        client.getAuthorizedGrantTypes().clear();
        client.getScope().clear();
        log.info("Update Client : " + objectMapper.writeValueAsString(client));
        ResponseEntity<ClientResource> responseEntity = restTemplate.exchange(basePath + "/admin/client", HttpMethod.PUT, RestRequestBuilder.build(client, accessToken), ClientResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(client = responseEntity.getBody());
        Assert.assertNotNull(client.getUid());
        Assert.assertEquals(0, client.getAuthorizedGrantTypes().size());
        Assert.assertEquals(0, client.getScope().size());
        log.info("Updated Client : " + objectMapper.writeValueAsString(client));
    }

    @Test
    public void testUpdateClientSecret() throws Exception {
        createClient();
        Assert.assertNotNull(client);
        Assert.assertNotNull(client.getUid());
        String clientSecret = UUID.randomUUID().toString();
        ResponseEntity<ClientResource> responseEntity = restTemplate.exchange(basePath + "/admin/client/" + client.getClientId() + "/secret?clientSecret="+clientSecret, HttpMethod.PUT, RestRequestBuilder.build(client, accessToken), ClientResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(client = responseEntity.getBody());
        Assert.assertNotNull(client.getUid());
        Assert.assertEquals(clientSecret, client.getClientSecret());
    }

    @Test
    public void testFindByClientId() throws Exception {
        createClient();
        String clientId = client.getClientId();
        log.info("Find Client with UID : " + clientId);
        ResponseEntity<ClientResource> responseEntity = restTemplate.exchange(basePath + "/admin/client/" + clientId, HttpMethod.GET, RestRequestBuilder.build(accessToken), ClientResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(client = responseEntity.getBody());
        Assert.assertNotNull(client.getUid());
        Assert.assertEquals(clientId, client.getClientId());
        log.info("Found : " + objectMapper.writeValueAsString(client));
    }

    @Test
    public void testFindAll() throws Exception {
        createClient();
        ResponseEntity<ClientResource[]> responseEntity = restTemplate.exchange(basePath + "/admin/clients", HttpMethod.GET, RestRequestBuilder.build(accessToken), ClientResource[].class);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertTrue(responseEntity.getBody().length > 0);
    }

    @After
    public void tearDown() throws Exception{
        if(client != null && client.getUid() != null) {
            ResponseEntity<Boolean> responseEntity = restTemplate.exchange(basePath + "/admin/client/" + client.getClientId(), HttpMethod.DELETE, RestRequestBuilder.build(accessToken), Boolean.class);
            Assert.assertNotNull(responseEntity);
            Assert.assertTrue(responseEntity.getBody());
            try {
                restTemplate.exchange(basePath + "/admin/client/" + client.getClientId(), HttpMethod.GET, RestRequestBuilder.build(accessToken), ClientResource.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                Assert.assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
            }
        }
    }
}