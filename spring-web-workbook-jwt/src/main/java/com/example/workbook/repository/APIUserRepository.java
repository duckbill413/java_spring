package com.example.workbook.repository;

import com.example.workbook.domain.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author        : duckbill413
 * date          : 2023-03-05
 * description   :
 **/

public interface APIUserRepository extends JpaRepository<APIUser, String> {
}
