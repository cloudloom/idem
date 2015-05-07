package com.tracebucket.idem.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.idem.IdemStarter;
import com.tracebucket.idem.rest.resource.ClientResource;
import com.tracebucket.idem.test.fixture.ClientResourceFixture;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by sadath on 30-Apr-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IdemStarter.class)
@WebIntegrationTest
public class ClientControllerTest {
    private static final Logger log = LoggerFactory.getLogger(ClientControllerTest.class);

    private RestTemplate restTemplate = null;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientResource client = null;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    private void createClient() throws Exception{
        client = ClientResourceFixture.standardClient();
        log.info("Create Client : " + objectMapper.writeValueAsString(client));
        client = restTemplate.postForObject(basePath+"/client", client, ClientResource.class);
        log.info("Created Client : " + objectMapper.writeValueAsString(client));
        Assert.assertNotNull(client);
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
        restTemplate.put(basePath+"/client", client);
        client = restTemplate.getForObject(basePath + "/client/" + client.getClientId(), ClientResource.class);
        Assert.assertNotNull(client);
        Assert.assertNotNull(client.getUid());
        Assert.assertEquals(0, client.getAuthorizedGrantTypes().size());
        Assert.assertEquals(0, client.getScope().size());
    }

    @Test
    public void testUpdateClientSecret() throws Exception {
        createClient();
        Assert.assertNotNull(client);
        Assert.assertNotNull(client.getUid());
        String clientSecret = client.getClientSecret();
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("clientSecret", UUID.randomUUID().toString());
        restTemplate.put(basePath+"/client/" + client.getClientId() + "/secret", null, requestParams);
        client = restTemplate.getForObject(basePath + "/client/" + client.getClientId(), ClientResource.class);
        Assert.assertNotNull(client);
        Assert.assertNotNull(client.getUid());
        Assert.assertEquals(clientSecret, client.getClientSecret());
    }

    @Test
    public void testFindByClientId() throws Exception {
        createClient();
        String clientId = client.getClientId();
        log.info("Find Client with UID : " + clientId);
        client = restTemplate.getForObject(basePath + "/client/" + clientId, ClientResource.class);
        Assert.assertNotNull(client);
        Assert.assertNotNull(client.getUid());
        Assert.assertEquals(clientId, client.getClientId());
        log.info("Found : " + objectMapper.writeValueAsString(client));
    }

    @Test
    public void testFindAll() throws Exception {
        createClient();
        ResponseEntity<ClientResource[]> responseEntity = restTemplate.getForEntity(basePath + "/clients", ClientResource[].class);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertTrue(responseEntity.getBody().length > 0);
    }

    @After
    public void tearDown() throws Exception{
        if(client != null && client.getUid() != null) {
            restTemplate.delete(basePath + "/client/" + client.getClientId());
            try {
                restTemplate.getForEntity(new URI(basePath + "/client/" + client.getClientId()), ClientResource.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                Assert.assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
            }
        }
    }
}