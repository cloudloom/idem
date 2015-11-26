package com.tracebucket.idem.repository.jpa;

import com.tracebucket.idem.domain.Tenant;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.tron.ddd.jpa.BaseEntityRepository;

/**
 * Created by Vishwajit on 03-08-2015.
 */
public interface TenantRepository  extends BaseEntityRepository<Tenant, EntityId>{

    public Tenant findByName(String name);
    public void deleteByName(String name);

}
