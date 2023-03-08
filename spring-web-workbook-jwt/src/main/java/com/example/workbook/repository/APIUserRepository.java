package com.example.workbook.repository;

import com.example.workbook.domain.APIUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * author        : duckbill413
 * date          : 2023-03-05
 * description   :
 **/

public interface APIUserRepository extends JpaRepository<APIUser, String> {
    @EntityGraph(attributePaths = "roleSet")
    @Query("select u from APIUser u where u.mid = :mid")
    Optional<APIUser> getWithRoles(@Param("mid") String mid);

    @EntityGraph(attributePaths = "roleSet")
    Optional<APIUser> findByMid(@Param("mid") String mid);
}
