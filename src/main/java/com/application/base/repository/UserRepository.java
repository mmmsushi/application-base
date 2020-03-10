package com.application.base.repository;

import com.application.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User getOneByEmail(@Param("email") String email);

    @Modifying
    @Query(value="UPDATE user u SET u.password = :newPassword WHERE u.email = :email", nativeQuery = true)
    void savePassword(@Param("email") String email, @Param("newPassword") String newPassword);
}
