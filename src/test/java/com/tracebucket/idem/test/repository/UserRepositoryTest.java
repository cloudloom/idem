package com.tracebucket.idem.test.repository;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.Group;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.repository.jpa.AuthorityRepository;
import com.tracebucket.idem.repository.jpa.GroupRepository;
import com.tracebucket.idem.repository.jpa.UserRepository;
import com.tracebucket.idem.test.config.ApplicationTestConfig;
import com.tracebucket.idem.test.config.JPATestConfig;
import com.tracebucket.idem.test.fixture.AuthorityFixture;
import com.tracebucket.idem.test.fixture.GroupFixture;
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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author ssm
 * @since 13-03-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, JPATestConfig.class})
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private GroupRepository groupRepository;

    private Set<Authority> authorities = new HashSet<Authority>();

    private Set<Group> groups = new HashSet<Group>();

    private User user;

    @Before
    public void setUp() {

    }

    private void createUser() {
        user = UserFixture.standardUser();
        user = userRepository.save(user);
    }

    @Test
    @Rollback(value = false)
    public void testCreate() {
        createUser();
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getEntityId());
    }

    @Test
    @Rollback(value = false)
    public void testUpdate() {
        createUser();
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getEntityId());
        Authority userAuthority = authorityRepository.findByRole(AuthorityFixture.userAuthority().getAuthority());
        Authority adminAuthority = authorityRepository.findByRole(AuthorityFixture.adminAuthority().getAuthority());
        if(userAuthority != null) {
            authorities.add(userAuthority);
        } else {
            authorities.add(authorityRepository.save(AuthorityFixture.userAuthority()));
        }
        if(adminAuthority != null) {
            authorities.add(adminAuthority);
        } else {
            authorities.add(authorityRepository.save(AuthorityFixture.adminAuthority()));
        }
        groups.add(groupRepository.save(GroupFixture.standardGroup()));
        user.setAuthorities(authorities);
        user.setGroups(groups);
        user = userRepository.save(user);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getEntityId());
        Assert.assertEquals(2, user.getAuthorities().size());
        Assert.assertEquals(1, user.getGroups().size());

    }

    @Test
    @Rollback(value = false)
    public void testFindOne() {
        createUser();
        user = userRepository.findOne(user.getEntityId());
        Assert.assertNotNull(user);
    }

    @Test
    @Rollback(value = false)
    public void testFindByUsername() {
        createUser();
        user = userRepository.findByUsername(user.getUsername());
        Assert.assertNotNull(user);
    }

    @Test
    @Rollback(value = false)
    public void testUpdatePassword() {
        createUser();
        String password = UUID.randomUUID().toString();
        userRepository.updatePassword(password, user.getUsername());
        user = userRepository.findByUsername(user.getUsername());
        Assert.assertNotNull(user);
        Assert.assertEquals(password, user.getPassword());
    }

    @After
    public void tearDown() {
        if(user != null && user.getEntityId() != null) {
            userRepository.delete(user.getEntityId());
            user = userRepository.findOne(user.getEntityId());
            Assert.assertNull(user);
        }
        if(authorities != null && authorities.size() > 0) {
            authorityRepository.delete(authorities);
        }
        if(groups != null && groups.size() > 0) {
            groupRepository.delete(groups);
        }
    }
}