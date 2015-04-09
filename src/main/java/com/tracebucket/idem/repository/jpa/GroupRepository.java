package com.tracebucket.idem.repository.jpa;

import com.tracebucket.idem.domain.Group;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.tron.ddd.jpa.BaseEntityRepository;

/**
 * Created by sadath on 17-Mar-15.
 */
public interface GroupRepository extends BaseEntityRepository<Group, EntityId> {
    public Group findByName(String name);
    public void deleteByName(String name);
}