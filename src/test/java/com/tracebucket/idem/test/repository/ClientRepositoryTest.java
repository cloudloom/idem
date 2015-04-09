package com.tracebucket.idem.test.repository;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.Client;
import com.tracebucket.idem.repository.jpa.AuthorityRepository;
import com.tracebucket.idem.repository.jpa.ClientRepository;
import com.tracebucket.idem.test.config.ApplicationTestConfig;
import com.tracebucket.idem.test.config.JPATestConfig;
import com.tracebucket.idem.test.fixture.AuthorityFixture;
import com.tracebucket.idem.test.fixture.ClientFixture;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author ssm
 * @since 13-03-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, JPATestConfig.class})
@Transactional
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    private Client client;

    private List<Authority> authorities = new ArrayList<Authority>();

    @Before
    public void setUp() {

    }

    private void createClient() {
        client = ClientFixture.standardClient();
        client = clientRepository.save(client);
    }

    @Test
    @Rollback(value = false)
    public void testCreate() {
        createClient();
        Assert.assertNotNull(client);
        Assert.assertNotNull(client.getEntityId());
    }

    @Test
    @Rollback(value = false)
    public void testUpdate() {
        createClient();
        Assert.assertNotNull(client);
        Assert.assertNotNull(client.getEntityId());
        Authority user = authorityRepository.findByRole(AuthorityFixture.userAuthority().getAuthority());
        Authority admin = authorityRepository.findByRole(AuthorityFixture.adminAuthority().getAuthority());
        if(user != null) {
            authorities.add(user);
        } else {
            authorities.add(authorityRepository.save(AuthorityFixture.userAuthority()));
        }
        if(admin != null) {
            authorities.add(admin);
        } else {
            authorities.add(authorityRepository.save(AuthorityFixture.adminAuthority()));
        }
        client.setAuthorities(authorities);
        client = clientRepository.save(client);
        Assert.assertNotNull(client);
        Assert.assertEquals(2, client.getAuthorities().size());
    }

    @Test
    @Rollback(value = false)
    public void testFindOne() {
        createClient();
        client = clientRepository.findOne(client.getEntityId());
        Assert.assertNotNull(client);
    }

    @Test
    @Rollback(value = false)
    public void testFindByClientId() {
        createClient();
        client = clientRepository.findByClientId(client.getClientId());
        Assert.assertNotNull(client);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteByClientId() {
        createClient();
        clientRepository.deleteByClientId(client.getClientId());
        client = clientRepository.findByClientId(client.getClientId());
        Assert.assertNull(client);
    }

    @After
    public void tearDown() {
        if(client != null && client.getEntityId() != null) {
            clientRepository.delete(client.getEntityId());
            client = clientRepository.findOne(client.getEntityId());
            Assert.assertNull(client);
        }
        if(authorities != null && authorities.size() > 0) {
            authorityRepository.delete(authorities);
        }
    }
}