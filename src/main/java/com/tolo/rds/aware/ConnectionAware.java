package com.tolo.rds.aware;

import java.sql.Connection;

/**
 * create by wly
 * date: 2020/05/20
 */
public interface ConnectionAware extends DbAware{

    Connection getConnection();

    void closeConnection(Connection connection);

}
