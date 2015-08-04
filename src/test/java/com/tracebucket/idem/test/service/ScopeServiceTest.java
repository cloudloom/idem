package com.tracebucket.idem.test.service;

import com.tracebucket.idem.IdemStarter;
import com.tracebucket.idem.domain.Scope;
import com.tracebucket.idem.service.impl.ScopeServiceImpl;
import com.tracebucket.idem.test.config.AccessTokenReceiverConfig;
import com.tracebucket.idem.test.fixture.ScopeFixture;
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

/**
 * Created by Vishwajit on 04-08-2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IdemStarter.class, AccessTokenReceiverConfig.class})
@WebIntegrationTest
public class ScopeServiceTest {

    @Autowired
    private ScopeServiceImpl scopeServiceImpl;

    private Scope scope = null;

    @Before
    public void setUp()throws Exception{

    }

    private void createScope()  {
        scope = ScopeFixture.standardScope();
        scope = scopeServiceImpl.save(scope);
    }

    @Test
    public void testCreate() throws Exception {
        createScope();
        Assert.assertNotNull(scope.getEntityId());
    }

    @Test
    public void testUpdate() throws Exception {
        createScope();
        Assert.assertNotNull(scope.getEntityId());

        scope.setName("updated_NAME");
        scope = scopeServiceImpl.update(scope);

        Assert.assertNotNull(scope);
        Assert.assertNotNull(scope.getEntityId());
        Assert.assertEquals("updated_NAME", scope.getName());
    }

    @Test
    @Rollback(value = false)
    public void testFindOne() {
        createScope();
        scope = scopeServiceImpl.findOne(scope.getEntityId().toString());
        Assert.assertNotNull(scope);
    }

    @Test
    @Rollback(value = false)
    public void testFindByName() {
        createScope();
        scope = scopeServiceImpl.findByName(scope.getName());
        Assert.assertNotNull(scope);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteByName() {
        createScope();
        scopeServiceImpl.deleteByName(scope.getName());
        scope = scopeServiceImpl.findByName(scope.getName());
        Assert.assertNull(scope);
    }

    @After
    public void tearDown() {
        if (scope != null && scope.getEntityId() != null) {
            scopeServiceImpl.delete(scope.getEntityId().toString());
            scope = scopeServiceImpl.findOne(scope.getEntityId().toString());
            Assert.assertNull(scope);
        }
    }
}
