package com.tracebucket.idem.repository.jpa;

import com.tracebucket.idem.domain.Approval;
import com.tracebucket.idem.domain.Client;
import com.tracebucket.idem.domain.User;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.tron.ddd.jpa.BaseEntityRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author FFL
 * @since 12-03-2015
 */
public interface ApprovalRepository extends BaseEntityRepository<Approval, EntityId> {
    public Approval findByClientAndUserAndScope(Client client, User user, String scope);

    @Query("Select a from com.tracebucket.idem.domain.Approval a where a.client.clientId = :clientId and a.user.username = :username and a.scope = :scope")
    public Approval findByClientAndUserAndScope(@Param("clientId") String clientId, @Param("username") String username,
            @Param("scope") String scope);

    @Query("Select a from com.tracebucket.idem.domain.Approval a where a.client.clientId = :clientId and a.user.username = :username")
    public List<Approval> findByUserNameAndClientId(@Param("username") String username,
            @Param("clientId") String clientId);

    @Modifying
    @Query(value = "update com.tracebucket.idem.domain.Approval a set a.expiresAt = :expiresAt where a.user.entityId = :userId and a.client.entityId = :clientId and a.scope = :scope")
    public int updateExpiresAtByClientIdAndUserIdAndScope(@Param("userId") EntityId userId,
            @Param("clientId") EntityId clientId, @Param("scope") String scope);

    @Modifying
    @Query(value = "delete from com.tracebucket.idem.domain.Approval a where a.user.entityId = :userId and a.client.entityId = :clientId and a.scope = :scope")
    public int deleteByClientIdAndUserIdAndScope(@Param("userId") EntityId userId, @Param("clientId") EntityId clientId,
            @Param("scope") String scope);


}
