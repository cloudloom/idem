package com.tracebucket.idem.test.repository;

import com.tracebucket.idem.domain.Scope;
import com.tracebucket.idem.repository.jpa.ScopeRepository;
import com.tracebucket.idem.repository.jpa.ScopeRepository;
import com.tracebucket.idem.test.config.ApplicationTestConfig;
import com.tracebucket.idem.test.config.JPATestConfig;
import com.tracebucket.idem.test.fixture.ScopeFixture;
import com.tracebucket.idem.test.fixture.ScopeFixture;
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
 * Created by Vishwajit on 04-08-2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes  = {ApplicationTestConfig.class, JPATestConfig.class})
@Transactional
public class ScopeRepositoryTest {

    @Autowired
    private ScopeRepository scopeRepository;

    private Scope scope;

    @Before
    public void setUp() {

    }

    private void createScope() {
        scope = ScopeFixture.standardScope();
        scope = scopeRepository.save(scope);
    }

    @Test
    @Rollback(value = false)
    public void testCreate() {
        createScope();
        Assert.assertNotNull(scope);
        Assert.assertNotNull(scope.getEntityId());
    }

    @Test
    @Rollback(value = false)
    public void testUpdate() {
        createScope();
        Assert.assertNotNull(scope);
        Assert.assertNotNull(scope.getEntityId());

        scope.setName("updated_NAME");
        scope = scopeRepository.save(scope);
        Assert.assertNotNull(scope);
        Assert.assertNotNull(scope.getEntityId());
        Assert.assertEquals("updated_NAME", scope.getName());
    }

    @Test
    @Rollback(value = false)
    public void testFindOne() {
        createScope();
        scope = scopeRepository.findOne(scope.getEntityId());
        Assert.assertNotNull(scope);
    }

    @Test
    @Rollback(value = false)
    public void testFindByName() {
        createScope();
        scope = scopeRepository.findByName(scope.getName());
        Assert.assertNotNull(scope);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteByName() {
        createScope();
        scopeRepository.deleteByName(scope.getName());
        scope = scopeRepository.findByName(scope.getName());
        Assert.assertNull(scope);
    }

    @After
    public void tearDown() {
        if (scope != null && scope.getEntityId() != null) {
            scopeRepository.delete(scope.getEntityId());
            scope = scopeRepository.findOne(scope.getEntityId());
            Assert.assertNull(scope);
        }
    }

}
