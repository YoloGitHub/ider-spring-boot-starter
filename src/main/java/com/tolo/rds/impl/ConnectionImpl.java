package com.tolo.rds.impl;

import com.tolo.rds.aware.ConnectionAware;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * create by wly
 * date: 2020/05/20
 */
@Data
@Slf4j
public  class ConnectionImpl implements ConnectionAware {

    private DataSource dataSource;

    @Override
    public Connection getConnection() {

        Connection connection = null;
        try {
            connection =this.dataSource.getConnection();
        } catch (SQLException e) {
            log.error("SQLException",e);
        }
        return connection;
    }

    @Override
    public void closeConnection(Connection connection) {

        try {
            connection.close();
        } catch (SQLException e) {
            log.error("SQLException", e);
        }
    }

}
