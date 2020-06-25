package com.tolo.rds.aware;

import java.sql.Connection;

/**
 * create by wly
 * date: 2020/05/20
 */
public interface DbTableAware extends ConnectionAware{

    void doCreate();

    void doReset(Connection connection);

    void doInsert(Connection connection);

    void doDelete(Connection connection);

    void doUpdate(Connection connection);

    Object[] doSelect(Connection connection);

}
