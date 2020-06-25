package com.tolo.idFactory.impl;

import com.tolo.core.aware.GenerateFactory;
import com.tolo.rds.aware.DbTableAware;
import com.tolo.rds.impl.ConnectionImpl;
import lombok.Data;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * create by wly
 * date: 2020/05/22
 */
@Data
public abstract class AbstractDbTableImpl extends ConnectionImpl implements DbTableAware, GenerateFactory {

//    private Object index;

    private static AtomicBoolean initOnceBl = new AtomicBoolean(false);

    public void init(){

        if (initOnceBl.compareAndSet(false, true)) {
            initOnce();
        }
    }

    /**
     * 如果有需要一次性动作的，类似static{}，可以实现此方法，同时需要初始化执行init();
     */
    public abstract void initOnce();

    @SneakyThrows
    @Override
    public synchronized Object generate() {

        Connection connection = getConnection();
        boolean oldCommit = connection.getAutoCommit();

        connection.setAutoCommit(false);
        Object[] sel = doSelect(connection);
        if(sel == null){
            doInsert(connection);
            sel = doSelect(connection);
        }
        doUpdate(connection);
        connection.commit();
        connection.setAutoCommit(oldCommit);

        closeConnection(connection);

        return sel;
    }

}
