package com.example.workbook;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * author        : duckbill413
 * date          : 2023-02-25
 * description   :
 **/
@SpringBootTest
@Slf4j
public class DataSourceTests {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() throws SQLException {
        @Cleanup
        Connection con = dataSource.getConnection();

        log.info(String.valueOf(con));
        Assertions.assertNotNull(con);
    }
}
