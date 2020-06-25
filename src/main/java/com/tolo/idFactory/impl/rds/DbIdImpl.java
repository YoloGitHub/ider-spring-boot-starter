package com.tolo.idFactory.impl.rds;

import com.tolo.idFactory.impl.AbstractDbTableImpl;
import lombok.Data;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;

/**
 * create by wly
 * date: 2020/05/22
 */
@Data
public class DbIdImpl extends AbstractDbTableImpl {

    private String idType;

    private Long start;

    private Long maxId;

    private Integer step;

    private String description;

    private Timestamp lastUpdateTms;

    public static final String CREATE_SQL = "create table Id_Factory(id_type varchar(50) not null,start bigint, max_id bigint,step int,description varchar(200),last_update_tms timestamp)";

    public static final String RESET_SQL = "update Id_Factory set max_id = 0 , last_update_tms = ? where id_type = ?";

    public static final String UPDATE_SQL = "update Id_Factory set max_id = max_id + ? , last_update_tms = ? where id_type = ?";

    public static final String INSERT_SQL = "insert into Id_Factory(id_type, start, max_id, step, description, last_update_tms)values(?, ?, ?, ?, ?, ?)";

    public static final String DELETE_SQL = "delete from Id_Factory where id_type = ?";

    public static final String SELECT_SQL = "select max_id from Id_Factory where id_type = ?";

    public Long cacheStart;

    public Integer cacheNum;

    /**
     * 因为是new出的对象，这里需要先把dataSource传进来，才能执行init里的建表语句
     * @param dataSource
     */
    public DbIdImpl(DataSource dataSource){

        this.setDataSource(dataSource);
    }

    /**
     * 因为是new出的对象，这里需要覆写一下该set方法
     * 为了第一次要查询一下数据库，获取cache; cacheNum = step + 1 通过这种方式触发
     * @param step
     */
    public void setStep(Integer step) {

        cacheNum = step + 1;
        this.step = step;
    }

    @Override
    public void initOnce() {
        doCreate();
    }

    @Override
    public synchronized String generateString() {

        if(cacheNum > step){
            cacheNum = 1;
            cacheStart = (Long)((Object[])generate())[0];
        }

        return ((Long)(start + cacheStart + cacheNum ++)).toString();
    }

    @SneakyThrows
    @Override
    public void doCreate() {

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        try {
            statement.execute(CREATE_SQL);
        } catch (SQLException e) {
        } finally {
            statement.close();
            closeConnection(connection);
        }
    }

    @SneakyThrows
    @Override
    public void doReset(Connection connection) {

        PreparedStatement preparedStatement = connection.prepareStatement(RESET_SQL);
        preparedStatement.setTimestamp(1, Timestamp.from(Instant.now()));
        preparedStatement.setString(2, idType);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @SneakyThrows
    @Override
    public void doInsert(Connection connection) {

        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
        preparedStatement.setString(1, idType);
        preparedStatement.setLong(2, start);
        preparedStatement.setLong(3, 0);
        preparedStatement.setInt(4, step);
        preparedStatement.setString(5, description);
        preparedStatement.setTimestamp(6, Timestamp.from(Instant.now()));
        preparedStatement.execute();
        preparedStatement.close();

    }

    @SneakyThrows
    @Override
    public void doDelete(Connection connection) {

        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
        preparedStatement.setString(1, idType);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @SneakyThrows
    @Override
    public void doUpdate(Connection connection) {

        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
        preparedStatement.setLong(1, step);
        preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setString(3, idType);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @SneakyThrows
    @Override
    public Object[] doSelect(Connection connection) {

        Object[] result = null;
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL);
        preparedStatement.setString(1, idType);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            if(result == null){
                result = new Object[1];
            }
            result[0] = rs.getLong(1);
        }
        rs.close();
        preparedStatement.close();

        return result;
    }

}
