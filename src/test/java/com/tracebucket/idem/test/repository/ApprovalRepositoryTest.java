package com.tracebucket.idem.test.repository;

import com.tracebucket.idem.domain.Approval;
import com.tracebucket.idem.domain.Client;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.repository.jpa.ApprovalRepository;
import com.tracebucket.idem.repository.jpa.ClientRepository;
import com.tracebucket.idem.repository.jpa.UserRepository;
import com.tracebucket.idem.test.config.ApplicationTestConfig;
import com.tracebucket.idem.test.config.JPATestConfig;
import com.tracebucket.idem.test.fixture.ApprovalFixture;
import com.tracebucket.idem.test.fixture.ClientFixture;
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

/**
 * @author ssm
 * @since 13-03-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, JPATestConfig.class})
@Transactional
public class ApprovalRepositoryTest {

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    private Approval approval;

    private Client client;

    private User user;

    @Before
    public void setUp() {

    }

    private void createApproval() {
        approval = ApprovalFixture.standardApproval();
        approval = approvalRepository.save(approval);
    }

    @Test
    @Rollback(value = false)
    public void testCreate() {
        createApproval();
        Assert.assertNotNull(approval);
        Assert.assertNotNull(approval.getEntityId());
    }

    @Test
    @Rollback(value = false)
    public void testUpdate() {
        createApproval();
        Assert.assertNotNull(approval);
        Assert.assertNotNull(approval.getEntityId());
        user = userRepository.save(UserFixture.standardUser());
        client = clientRepository.save(ClientFixture.tempClient());
        approval.setUser(user);
        approval.setClient(client);
        approval = approvalRepository.save(approval);
        Assert.assertNotNull(approval);
        Assert.assertNotNull(approval.getEntityId());
        Assert.assertNotNull(approval.getUser());
        Assert.assertNotNull(approval.getClient());
    }

    @Test
    @Rollback(value = false)
    public void testFindOne() {
        createApproval();
        approval = approvalRepository.findOne(approval.getEntityId());
        Assert.assertNotNull(approval);
    }

    @After
    public void tearDown() {
        if(approval != null && approval.getEntityId() != null) {
            approvalRepository.delete(approval.getEntityId());
            approval = approvalRepository.findOne(approval.getEntityId());
            Assert.assertNull(approval);
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