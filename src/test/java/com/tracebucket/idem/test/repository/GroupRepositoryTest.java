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
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ssm
 * @since 13-03-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes  = {ApplicationTestConfig.class, JPATestConfig.class})
@Transactional
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    private Group group;

    private Set<Authority> authorities = new HashSet<Authority>();

    private Set<User> members = new HashSet<User>();

    @Before
    public void setUp() {

    }

    private void createGroup() {
        group = GroupFixture.standardGroup();
        group = groupRepository.save(group);
    }

    @Test
    @Rollback(value = false)
    public void testCreate() {
        createGroup();
        Assert.assertNotNull(group);
        Assert.assertNotNull(group.getEntityId());
    }

    @Test
    @Rollback(value = false)
    public void testUpdate() {
        createGroup();
        Assert.assertNotNull(group);
        Assert.assertNotNull(group.getEntityId());
        members.add(userRepository.save(UserFixture.standardUser()));
        authorities.add(authorityRepository.save(AuthorityFixture.userAuthority()));
        group.getAuthorities().addAll(authorities);
        group.getMembers().addAll(members);
        group = groupRepository.save(group);
        Assert.assertNotNull(group);
        Assert.assertNotNull(group.getEntityId());
        Assert.assertEquals(1, group.getAuthorities().size());
        Assert.assertEquals(1, group.getMembers().size());
    }

    @Test
    @Rollback(value = false)
    public void testFindOne() {
        createGroup();
        group = groupRepository.findOne(group.getEntityId());
        Assert.assertNotNull(group);
    }

    @Test
    @Rollback(value = false)
    public void testFindByName() {
        createGroup();
        group = groupRepository.findByName(group.getName());
        Assert.assertNotNull(group);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteByName() {
        createGroup();
        groupRepository.deleteByName(group.getName());
        group = groupRepository.findByName(group.getName());
        Assert.assertNull(group);
    }

    @After
    public void tearDown() {
        if(group != null && group.getEntityId() != null) {
            groupRepository.delete(group.getEntityId());
            group = groupRepository.findOne(group.getEntityId());
            Assert.assertNull(group);
        }
        if(members != null && members.size() > 0) {
            userRepository.delete(members);
        }
        if(authorities != null && authorities.size() > 0) {
            authorityRepository.delete(authorities);
        }
    }
}
