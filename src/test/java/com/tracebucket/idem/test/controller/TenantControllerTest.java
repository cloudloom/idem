package com.tracebucket.idem.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.idem.IdemStarter;
import com.tracebucket.idem.rest.resource.TenantResource;
import com.tracebucket.idem.test.config.AccessTokenReceiverConfig;
import com.tracebucket.idem.test.fixture.TenantResourceFixture;
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
 * Created by Vishwajit on 03-08-2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IdemStarter.class, AccessTokenReceiverConfig.class})
@WebIntegrationTest
public class TenantControllerTest {


    private static final Logger log = LoggerFactory.getLogger(TenantControllerTest.class);

    private RestTemplate restTemplate = null;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccessTokenReceiverConfig accessTokenReceiver;

    private String accessToken = null;

    private TenantResource tenant;

    @Before
    public void setUp() {
        accessToken = accessTokenReceiver.receive("idem-admin", "idem-admin-secret", "admin", "admin");
        restTemplate = new RestTemplate();
    }

    private void createTenant() throws Exception{
        tenant = TenantResourceFixture.standardTenant();
        log.info("Create Tenant : " + objectMapper.writeValueAsString(tenant));
        ResponseEntity<TenantResource> responseEntity = restTemplate.exchange(basePath + "/admin/tenant", HttpMethod.POST, RestRequestBuilder.build(tenant, accessToken), TenantResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(tenant = responseEntity.getBody());
        log.info("Created Tenant : " + objectMapper.writeValueAsString(tenant));
    }

    @Test
    public void testCreate() throws Exception {
        createTenant();
        Assert.assertNotNull(tenant.getUid());
    }

    @Test
    public void testUpdate() throws Exception {
        createTenant();
        tenant.setName("Updated_Name");
        ResponseEntity<TenantResource> responseEntity = restTemplate.exchange(basePath + "/admin/tenant/update", HttpMethod.PUT, RestRequestBuilder.build(tenant, accessToken), TenantResource.class);

        Assert.assertNotNull(tenant.getUid());
        Assert.assertEquals("Updated_Name", responseEntity.getBody().getName());
    }

    @Test
    public void testFindOne() throws Exception {
        createTenant();
        ResponseEntity<TenantResource> responseEntity = restTemplate.exchange(basePath + "/admin/tenant/"+tenant.getUid(), HttpMethod.GET, RestRequestBuilder.build(tenant, accessToken), TenantResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void testFindByName() throws Exception {
        createTenant();
        ResponseEntity<TenantResource> responseEntity = restTemplate.exchange(basePath + "/admin/tenants/"+tenant.getName(), HttpMethod.GET, RestRequestBuilder.build(tenant, accessToken), TenantResource.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertNotNull(responseEntity.getBody().getName());
    }

    @Test
    public void testDeleteByName() throws Exception {
        createTenant();
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(basePath + "/admin/tenant/delete/"+tenant.getName(), HttpMethod.DELETE, RestRequestBuilder.build(tenant, accessToken), Boolean.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertTrue(responseEntity.getBody());
        tenant = null;
    }

    @Test
    public void testDelete() throws Exception {
        createTenant();
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(basePath + "/admin/tenant/"+tenant.getUid(), HttpMethod.DELETE, RestRequestBuilder.build(tenant, accessToken), Boolean.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertTrue(responseEntity.getBody());
        tenant = null;
    }

    @After
    public void tearDown() throws Exception {
        if (tenant != null && tenant.getUid() != null) {
            ResponseEntity<Boolean> responseEntity = restTemplate.exchange(basePath + "/admin/tenant/delete/" + tenant.getName(), HttpMethod.DELETE, RestRequestBuilder.build(accessToken), Boolean.class);
            Assert.assertNotNull(responseEntity);
            Assert.assertTrue(responseEntity.getBody());
            try {
                restTemplate.exchange(basePath + "/admin/tenants/" + tenant.getName(), HttpMethod.GET, RestRequestBuilder.build(accessToken), TenantResource.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                Assert.assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
            }
        }
    }

}
