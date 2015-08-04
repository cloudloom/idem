package com.tracebucket.idem.service.impl;

import com.tracebucket.idem.domain.Scope;
import com.tracebucket.idem.repository.jpa.ScopeRepository;
import com.tracebucket.tron.ddd.domain.EntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Vishwajit on 04-08-2015.
 */
@Service("scopeServiceImpl")
@Transactional
public class ScopeServiceImpl {

    @Autowired
    ScopeRepository scopeRepository;

    public Scope save(Scope scope) {
        return scopeRepository.save(scope);
    }

    public Scope update(Scope scope) {
        return scopeRepository.save(scope);
    }

    public Scope findOne(String uid) {
        return scopeRepository.findOne(new EntityId(uid));
    }

    public Scope findByName(String name) {
        return scopeRepository.findByName(name);
    }

    public boolean deleteByName(String scopeName) {
        Scope scope = findByName(scopeName);
        if(scope != null) {
            scopeRepository.deleteByName(scope.getName());
            return scopeRepository.findByName(scope.getName()) != null ? false : true;
        }
        return false;
    }

    public boolean delete(String uid) {
        Scope scope = findOne(uid);
        if(scope != null) {
            scopeRepository.delete(scope);
            return scopeRepository.findOne(new EntityId(uid)) == null ? true : false;
        }
        return false;
    }

    public boolean deleteAll() {
        scopeRepository.deleteAll();
        return scopeRepository.findAll() != null && scopeRepository.findAll().size() == 0 ? true : false;
    }
}
