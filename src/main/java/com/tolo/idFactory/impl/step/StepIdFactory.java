package com.tolo.idFactory.impl.step;

import com.tolo.core.aware.GenerateFactory;
import com.tolo.idFactory.impl.AbstractStepFactory;
import com.tolo.idFactory.impl.rds.DbIdImpl;
import lombok.Data;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * create by wly
 * date: 2020/05/22
 */
@Data
public class StepIdFactory extends AbstractStepFactory {

    private DataSource dataSource;

    @Override
    public GenerateFactory cacheBean(String param) {

        String[] strs = param.split(getSeparator(),-1);
        DbIdImpl dbIdImpl = new DbIdImpl(dataSource);
        dbIdImpl.init();
        dbIdImpl.setIdType(strs[0]);
        dbIdImpl.setStart(Long.parseLong(strs[1]));
        dbIdImpl.setMaxId(0L);
        dbIdImpl.setStep(Integer.parseInt(strs[2]));
        dbIdImpl.setDescription(strs[3]);
        dbIdImpl.setLastUpdateTms(Timestamp.from(Instant.now()));

        return dbIdImpl;
    }

}
