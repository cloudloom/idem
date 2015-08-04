package com.tracebucket.idem.test.service;

import com.tracebucket.idem.IdemStarter;
import com.tracebucket.idem.domain.Tenant;
import com.tracebucket.idem.service.impl.TenantServiceImpl;
import com.tracebucket.idem.test.config.AccessTokenReceiverConfig;
import com.tracebucket.idem.test.config.ApplicationTestConfig;
import com.tracebucket.idem.test.config.JPATestConfig;
import com.tracebucket.idem.test.config.WebTestConfig;
import com.tracebucket.idem.test.fixture.TenantFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Vishwajit on 03-08-2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IdemStarter.class, AccessTokenReceiverConfig.class})
@WebIntegrationTest
public class TenantServiceTest {

    @Autowired
    private TenantServiceImpl tenantServiceImpl;

    private Tenant tenant = null;

    @Before
    public void setUp()throws Exception{

    }

    private void createTenant()  {
        tenant = TenantFixture.standardTenant();
        tenant = tenantServiceImpl.save(tenant);
    }

    @Test
    public void testCreate() throws Exception {
        createTenant();
        Assert.assertNotNull(tenant.getEntityId());
    }

    @Test
    public void testUpdate() throws Exception {
        createTenant();
        Assert.assertNotNull(tenant.getEntityId());

        tenant.setName("updated_NAME");
        tenant = tenantServiceImpl.update(tenant);

        Assert.assertNotNull(tenant);
        Assert.assertNotNull(tenant.getEntityId());
        Assert.assertEquals("updated_NAME", tenant.getName());
    }

    @Test
    @Rollback(value = false)
    public void testFindOne() {
        createTenant();
        tenant = tenantServiceImpl.findOne(tenant.getEntityId().toString());
        Assert.assertNotNull(tenant);
    }

    @Test
    @Rollback(value = false)
    public void testFindByName() {
        createTenant();
        tenant = tenantServiceImpl.findByName(tenant.getName());
        Assert.assertNotNull(tenant);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteByName() {
        createTenant();
        tenantServiceImpl.deleteByName(tenant.getName());
        tenant = tenantServiceImpl.findByName(tenant.getName());
        Assert.assertNull(tenant);
    }

    @After
    public void tearDown() {
        if (tenant != null && tenant.getEntityId() != null) {
            tenantServiceImpl.delete(tenant.getEntityId().toString());
            tenant = tenantServiceImpl.findOne(tenant.getEntityId().toString());
            Assert.assertNull(tenant);
        }
    }
}
