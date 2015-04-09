package com.tracebucket.idem.test.repository;

import com.tracebucket.idem.domain.AccessToken;
import com.tracebucket.idem.domain.RefreshToken;
import com.tracebucket.idem.repository.jpa.AccessTokenRepository;
import com.tracebucket.idem.repository.jpa.RefreshTokenRepository;
import com.tracebucket.idem.test.config.ApplicationTestConfig;
import com.tracebucket.idem.test.config.JPATestConfig;
import com.tracebucket.idem.test.fixture.RefreshTokenFixture;
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
public class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    private RefreshToken refreshToken;

    private AccessToken accessToken;

    @Before
    public void setUp() {

    }

    private void createRefreshToken() {
        refreshToken = RefreshTokenFixture.standardRefreshToken();
        refreshToken = refreshTokenRepository.save(refreshToken);
    }

    @Test
    @Rollback(value = false)
    public void testCreate() {
        createRefreshToken();
        Assert.assertNotNull(refreshToken);
        Assert.assertNotNull(refreshToken.getTokenId());
    }

    @Test
    @Rollback(value = false)
    public void testUpdate() {
        createRefreshToken();
        Assert.assertNotNull(refreshToken);
        Assert.assertNotNull(refreshToken.getTokenId());
    }

    @Test
    @Rollback(value = false)
    public void testFindOne() {
        createRefreshToken();
        refreshToken = refreshTokenRepository.findOne(refreshToken.getTokenId());
        Assert.assertNotNull(refreshToken);
    }

    @Test
    @Rollback(value = false)
    public void testFindByTokenId() {
        createRefreshToken();
        refreshToken = refreshTokenRepository.findByTokenId(refreshToken.getTokenId());
        Assert.assertNotNull(refreshToken);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteByTokenId() {
        createRefreshToken();
        refreshTokenRepository.deleteByTokenId(refreshToken.getTokenId());
        refreshToken = refreshTokenRepository.findByTokenId(refreshToken.getTokenId());
        Assert.assertNull(refreshToken);
    }

    @After
    public void tearDown() {
        if(refreshToken != null && refreshToken.getTokenId() != null) {
            refreshTokenRepository.delete(refreshToken.getTokenId());
            refreshToken = refreshTokenRepository.findOne(refreshToken.getTokenId());
            Assert.assertNull(refreshToken);
        }
    }
}