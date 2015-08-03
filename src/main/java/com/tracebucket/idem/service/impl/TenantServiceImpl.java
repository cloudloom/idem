package com.tracebucket.idem.service.impl;

import com.tracebucket.idem.domain.Tenant;
import com.tracebucket.idem.repository.jpa.TenantRepository;
import com.tracebucket.tron.ddd.domain.EntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by Vishwajit on 03-08-2015.
 */
@Service("tenantServiceImpl")
@Transactional
public class TenantServiceImpl{

    @Autowired
    TenantRepository tenantRepository;

    public Tenant save(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public Tenant update(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public Tenant findOne(String uid) {
        return tenantRepository.findOne(new EntityId(uid));
    }

    public Tenant findByName(String name) {
        return tenantRepository.findByName(name);
    }

    public boolean deleteByName(Tenant tenant) {
        tenantRepository.deleteByName(tenant.getName());
        return tenantRepository.findByName(tenant.getName()) != null ? false : true;
    }

    public boolean delete(String uid) {
        tenantRepository.delete(new EntityId(uid));
        return tenantRepository.findOne(new EntityId(uid)) == null ? true : false;
    }

    public boolean deleteAll() {
        tenantRepository.deleteAll();
        return tenantRepository.findAll().size() == 0 ? true : false;
    }
}
