package com.tracebucket.idem.service.impl;

import com.tracebucket.idem.domain.Tenant;
import com.tracebucket.idem.repository.jpa.TenantRepository;
import com.tracebucket.tron.ddd.domain.EntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

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

    public List<Tenant> findAll() {
        return tenantRepository.findAll();
    }

    public Tenant findByName(String name) {
        return tenantRepository.findByName(name);
    }

    public boolean deleteByName(String tenantName) {
        Tenant tenant = findByName(tenantName);
        if(tenant != null) {
            tenantRepository.deleteByName(tenant.getName());
            return tenantRepository.findByName(tenant.getName()) != null ? false : true;
        }
        return false;
    }

    public boolean delete(String uid) {
        Tenant tenant = findOne(uid);
        if(tenant != null) {
            tenantRepository.delete(tenant);
            return tenantRepository.findOne(new EntityId(uid)) == null ? true : false;
        }
        return false;
    }

    public boolean deleteAll() {
        tenantRepository.deleteAll();
        return tenantRepository.findAll() != null && tenantRepository.findAll().size() == 0 ? true : false;
    }
}
