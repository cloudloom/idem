package com.tracebucket.idem.service.impl;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.repository.jpa.AuthorityRepository;
import com.tracebucket.tron.ddd.domain.EntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sadath on 13-May-15.
 */
@Service("authorityServiceImpl")
@Transactional
public class AuthorityServiceImpl {

    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    public Authority update(Authority authority) {
        return authorityRepository.save(authority);
    }

    public Authority findOne(String uid) {
        return authorityRepository.findOne(new EntityId(uid));
    }

    public Authority findByRole(String role) {
        return authorityRepository.findByRole(role);
    }

    public List<Authority> findByRoleIn(List<String> roles) {
        return authorityRepository.findByRoleIn(roles);
    }

    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    public boolean delete(String uid) {
        authorityRepository.delete(new EntityId(uid));
        return authorityRepository.findOne(new EntityId(uid)) == null ? true : false;
    }

    public boolean deleteAll() {
        authorityRepository.deleteAll();
        return authorityRepository.findAll().size() == 0 ? true : false;
    }

}