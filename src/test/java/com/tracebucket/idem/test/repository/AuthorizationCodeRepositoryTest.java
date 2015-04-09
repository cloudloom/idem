package com.tracebucket.idem.test.repository;

import com.tracebucket.idem.domain.AuthorizationCode;
import com.tracebucket.idem.repository.jpa.AuthorizationCodeRepository;
import com.tracebucket.idem.test.config.ApplicationTestConfig;
import com.tracebucket.idem.test.config.JPATestConfig;
import com.tracebucket.idem.test.fixture.AuthorizationCodeFixture;
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
public class AuthorizationCodeRepositoryTest {

    @Autowired
    private AuthorizationCodeRepository authorizationCodeRepository;

    private AuthorizationCode authorizationCode;

    @Before
    public void setUp() {

    }

    private void createAuthorizationCode() {
        authorizationCode = AuthorizationCodeFixture.standardAuthorizationCode();
        authorizationCode = authorizationCodeRepository.save(authorizationCode);
    }

    @Test
    @Rollback(value = false)
    public void testCreate() {
        createAuthorizationCode();
        Assert.assertNotNull(authorizationCode);
        Assert.assertNotNull(authorizationCode.getEntityId());
    }

    @Test
    @Rollback(value = false)
    public void testUpdate() {
        createAuthorizationCode();
        Assert.assertNotNull(authorizationCode);
        Assert.assertNotNull(authorizationCode.getEntityId());
    }

    @Test
    @Rollback(value = false)
    public void testFindOne() {
        createAuthorizationCode();
        authorizationCode = authorizationCodeRepository.findOne(authorizationCode.getEntityId());
        Assert.assertNotNull(authorizationCode);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteByCode(){
        authorizationCode = AuthorizationCodeFixture.standardAuthorizationCode();
        authorizationCode = authorizationCodeRepository.save(authorizationCode);
        authorizationCodeRepository.deleteByCode(authorizationCode.getCode());
    }

    @After
    public void tearDown() {
        if(authorizationCode != null && authorizationCode.getEntityId() != null) {
            authorizationCodeRepository.deleteByCode(authorizationCode.getCode());
            authorizationCode = authorizationCodeRepository.findOne(authorizationCode.getEntityId());
            Assert.assertNull(authorizationCode);
        }
    }
}