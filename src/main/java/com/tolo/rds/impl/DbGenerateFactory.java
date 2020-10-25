package com.tolo.rds.impl;

import com.tolo.core.aware.GenerateFactory;
import com.tolo.rds.aware.DbTableAware;
import lombok.Data;
import lombok.SneakyThrows;

import java.sql.Connection;

/**
 * 暂无用
 * create by wly
 * date: 2020/05/21
 */
@Data
public class DbGenerateFactory implements GenerateFactory {

    private DbTableAware dbTableAware;

    private Object[] values;

    @SneakyThrows
//    @Override
    public Object generate() {

        Connection connection = dbTableAware.getConnection();
        boolean oldCommit = connection.getAutoCommit();

        connection.setAutoCommit(false);
        Object[] sel = dbTableAware.doSelect(connection);
//        if (seq[0] == -1L) {
        if(sel == null){
            dbTableAware.doInsert(connection);
        }
        dbTableAware.doUpdate(connection);
        connection.commit();
        connection.setAutoCommit(oldCommit);

        return null;
    }

    @Override
    public Long generateLong() {
        return null;
    }

    @Override
    public String generateString() {
        return null;
    }

}
