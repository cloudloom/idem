package com.tracebucket.idem.repository.jpa;

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
public interface UserRepository extends BaseEntityRepository<User,EntityId> {
    //public User findByUsername(String username);

    @Query(value = "select u from com.tracebucket.idem.domain.User u where u.username = :username")
    public User findByUsername(@Param("username") String username);

    @Query(value = "select u from com.tracebucket.idem.domain.User u where u.username IN (:userNames)")
    public List<User> findByUserNames(@Param("userNames") List<String> userNames);

    public void deleteByUsername(String username);

    @Modifying(clearAutomatically = true)
    @Query(value = "update com.tracebucket.idem.domain.User u set u.password = :newPassword where u.username = :username")
    public void updatePassword(@Param("newPassword") String newPassword, @Param("username") String username);
}
