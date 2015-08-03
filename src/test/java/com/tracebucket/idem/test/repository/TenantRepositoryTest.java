package com.tracebucket.idem.test.repository;

import com.tracebucket.idem.domain.Tenant;
import com.tracebucket.idem.repository.jpa.TenantRepository;
import com.tracebucket.idem.test.config.ApplicationTestConfig;
import com.tracebucket.idem.test.config.JPATestConfig;
import com.tracebucket.idem.test.fixture.TenantFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

/**
 * Created by Vishwajit on 03-08-2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes  = {ApplicationTestConfig.class, JPATestConfig.class})
@Transactional
public class TenantRepositoryTest {

    @Autowired
    private TenantRepository tenantRepository;

    private Tenant tenant;

    @Before
    public void setUp() {

    }

    private void createTenant() {
        tenant = TenantFixture.standardTenant();
        tenant = tenantRepository.save(tenant);
    }

    @Test
    @Rollback(value = false)
    public void testCreate() {
        createTenant();
        Assert.assertNotNull(tenant);
        Assert.assertNotNull(tenant.getEntityId());
    }

    @Test
    @Rollback(value = false)
    public void testUpdate() {
        createTenant();
        Assert.assertNotNull(tenant);
        Assert.assertNotNull(tenant.getEntityId());

        tenant.setName("updated_NAME");
        tenant = tenantRepository.save(tenant);
        Assert.assertNotNull(tenant);
        Assert.assertNotNull(tenant.getEntityId());
        Assert.assertEquals("updated_NAME", tenant.getName());
    }

    @Test
    @Rollback(value = false)
    public void testFindOne() {
        createTenant();
        tenant = tenantRepository.findOne(tenant.getEntityId());
        Assert.assertNotNull(tenant);
    }

    @Test
    @Rollback(value = false)
    public void testFindByName() {
        createTenant();
        tenant = tenantRepository.findByName(tenant.getName());
        Assert.assertNotNull(tenant);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteByName() {
        createTenant();
        tenantRepository.deleteByName(tenant.getName());
        tenant = tenantRepository.findByName(tenant.getName());
        Assert.assertNull(tenant);
    }

    @After
    public void tearDown() {
        if (tenant != null && tenant.getEntityId() != null) {
            tenantRepository.delete(tenant.getEntityId());
            tenant = tenantRepository.findOne(tenant.getEntityId());
            Assert.assertNull(tenant);
        }
    }
}
