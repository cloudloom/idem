package com.tracebucket.idem.test.repository;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.repository.jpa.AuthorityRepository;
import com.tracebucket.idem.test.config.ApplicationTestConfig;
import com.tracebucket.idem.test.config.JPATestConfig;
import com.tracebucket.idem.test.fixture.AuthorityFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

/**
 * @author ssm
 * @since 13-03-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, JPATestConfig.class})
@Transactional
public class AuthorityRepositoryTest {

    @Autowired
    private AuthorityRepository authorityRepository;

    private Authority authority;

    @Before
    public void setUp() {

    }

    private void createAuthority() {
        authority = AuthorityFixture.userAuthority();
        authority = authorityRepository.save(authority);
    }

    @Test
    @Rollback(value = false)
    public void testCreate() {
        createAuthority();
        Assert.assertNotNull(authority);
        Assert.assertNotNull(authority.getEntityId());
    }

    @Test
    @Rollback(value = false)
    public void testUpdate() {
        createAuthority();
        Assert.assertNotNull(authority);
        Assert.assertNotNull(authority.getEntityId());
    }

    @Test
    @Rollback(value = false)
    public void testFindOne() {
        createAuthority();
        authority = authorityRepository.findOne(authority.getEntityId());
        Assert.assertNotNull(authority);
    }

    @After
    public void tearDown() {
        if(authority != null && authority.getEntityId() != null) {
            authorityRepository.delete(authority.getEntityId());
            authority = authorityRepository.findOne(authority.getEntityId());
            Assert.assertNull(authority);
        }
    }
}