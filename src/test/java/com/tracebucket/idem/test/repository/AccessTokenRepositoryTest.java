package com.tracebucket.idem.test.repository;

import com.tracebucket.idem.domain.AccessToken;
import com.tracebucket.idem.domain.Client;
import com.tracebucket.idem.domain.RefreshToken;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.repository.jpa.AccessTokenRepository;
import com.tracebucket.idem.repository.jpa.ClientRepository;
import com.tracebucket.idem.repository.jpa.RefreshTokenRepository;
import com.tracebucket.idem.repository.jpa.UserRepository;
import com.tracebucket.idem.test.config.ApplicationTestConfig;
import com.tracebucket.idem.test.config.JPATestConfig;
import com.tracebucket.idem.test.fixture.AccessTokenFixture;
import com.tracebucket.idem.test.fixture.ClientFixture;
import com.tracebucket.idem.test.fixture.RefreshTokenFixture;
import com.tracebucket.idem.test.fixture.UserFixture;
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
import java.util.List;

/**
 * @author ssm
 * @since 13-03-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, JPATestConfig.class})
@Transactional
public class AccessTokenRepositoryTest {

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private AccessToken accessToken;

    private RefreshToken refreshToken;

    private Client client;

    private User user;

    @Before
    public void setUp() {

    }

    private void createAccessToken() {
        accessToken = AccessTokenFixture.standardAccessToken();
        accessToken = accessTokenRepository.save(accessToken);
    }

    @Test
    @Rollback(value = false)
    public void testCreate() {
        createAccessToken();
        Assert.assertNotNull(accessToken);
        Assert.assertNotNull(accessToken.getAuthenticationId());
    }

    @Test
    @Rollback(value = false)
    public void testUpdate() {
        createAccessToken();
        Assert.assertNotNull(accessToken);
        Assert.assertNotNull(accessToken.getAuthenticationId());
        user = userRepository.save(UserFixture.standardUser());
        client = clientRepository.save(ClientFixture.standardClient());
        refreshToken = refreshTokenRepository.save(RefreshTokenFixture.standardRefreshToken());
        accessToken.setUser(user);
        accessToken.setClient(client);
        accessToken.setRefreshToken(refreshToken);
        accessToken = accessTokenRepository.save(accessToken);
        Assert.assertNotNull(accessToken);
        Assert.assertNotNull(accessToken.getAuthenticationId());
        Assert.assertNotNull(accessToken.getUser());
        Assert.assertNotNull(accessToken.getClient());
        Assert.assertNotNull(accessToken.getRefreshToken());
    }

    @Test
    @Rollback(value = false)
    public void testFindOne() {
        createAccessToken();
        accessToken = accessTokenRepository.findOne(accessToken.getAuthenticationId());
        Assert.assertNotNull(accessToken);
    }

    @Test
    @Rollback(value = false)
    public void testFindByTokenId() {
        createAccessToken();
        accessToken = accessTokenRepository.findByTokenId(accessToken.getTokenId());
        Assert.assertNotNull(accessToken);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteByTokenId() {
        createAccessToken();
        accessTokenRepository.deleteByTokenId(accessToken.getTokenId());
        accessToken = accessTokenRepository.findByTokenId(accessToken.getTokenId());
        Assert.assertNull(accessToken);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteByRefreshTokenTokenId() {
        createAccessToken();
        refreshToken = refreshTokenRepository.save(RefreshTokenFixture.standardRefreshToken());
        accessToken.setRefreshToken(refreshToken);
        accessToken = accessTokenRepository.save(accessToken);
        accessTokenRepository.deleteByRefreshTokenTokenId(((RefreshToken)accessToken.getRefreshToken()).getTokenId());
        accessToken = accessTokenRepository.findByTokenId(accessToken.getTokenId());
        Assert.assertNull(accessToken);
    }

    @Test
    @Rollback(value = false)
    public void testFindByAuthenticationId() {
        createAccessToken();
        accessToken = accessTokenRepository.findByAuthenticationId(accessToken.getAuthenticationId());
        Assert.assertNotNull(accessToken);
    }

    @Test
    @Rollback(value = false)
    public void testFindByUserAndClient() {
        createAccessToken();
        Assert.assertNotNull(accessToken);
        Assert.assertNotNull(accessToken.getAuthenticationId());
        user = userRepository.save(UserFixture.standardUser());
        client = clientRepository.save(ClientFixture.standardClient());
        accessToken.setUser(user);
        accessToken.setClient(client);
        accessToken = accessTokenRepository.save(accessToken);
        List<AccessToken> accessTokens = accessTokenRepository.findByUserAndClient(accessToken.getUser().getUsername(), accessToken.getClient().getClientId());
        Assert.assertNotNull(accessTokens);
    }

    @Test
    @Rollback(value = false)
    public void testFindByClient() {
        createAccessToken();
        Assert.assertNotNull(accessToken);
        Assert.assertNotNull(accessToken.getAuthenticationId());
        client = clientRepository.save(ClientFixture.standardClient());
        accessToken.setClient(client);
        accessToken = accessTokenRepository.save(accessToken);
        List<AccessToken> accessTokens = accessTokenRepository.findByClient(accessToken.getClient().getClientId());
        Assert.assertNotNull(accessTokens);
    }

    @After
    public void tearDown() {
        if(accessToken != null && accessToken.getAuthenticationId() != null) {
            accessTokenRepository.delete(accessToken.getAuthenticationId());
            accessToken = accessTokenRepository.findOne(accessToken.getAuthenticationId());
            Assert.assertNull(accessToken);
        }
        if(client != null && client.getEntityId() != null) {
            clientRepository.delete(client.getEntityId());
            client = clientRepository.findOne(client.getEntityId());
            Assert.assertNull(client);
        }
        if(user != null && user.getEntityId() != null) {
            userRepository.delete(user.getEntityId());
            user = userRepository.findOne(user.getEntityId());
            Assert.assertNull(user);
        }
    }

}