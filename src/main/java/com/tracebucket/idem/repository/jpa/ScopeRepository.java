package com.tracebucket.idem.repository.jpa;

import com.tracebucket.idem.domain.Scope;

import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.tron.ddd.jpa.BaseEntityRepository;

/**
 * Created by Vishwajit on 04-08-2015.
 */
public interface ScopeRepository extends BaseEntityRepository<Scope, EntityId> {

    public Scope findByName(String name);
    public void deleteByName(String name);
}
