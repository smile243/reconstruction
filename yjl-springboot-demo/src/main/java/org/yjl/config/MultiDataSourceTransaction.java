package org.yjl.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @author: yjl
 * @date: 2023年05月12日 8:39
 */
public class MultiDataSourceTransaction implements Transaction {
    private final DataSource dataSource;

    private ConcurrentHashMap<String, Connection> connectionMap;

    private boolean autoCommit;

    public MultiDataSourceTransaction(DataSource dataSource) {
        this.dataSource = dataSource;
        connectionMap = new ConcurrentHashMap<>();
    }


    @Override
    public Connection getConnection() throws SQLException {
        //获取数据源类型信息
        String databaseIdentification = DynamicDataSourceContextHolder.peek();
        if (StrUtil.isEmpty(databaseIdentification)) {
            databaseIdentification = "master";
        }
        if (!this.connectionMap.containsKey(databaseIdentification)) {
            try {
                Connection connection = this.dataSource.getConnection();
                autoCommit = false;
                connection.setAutoCommit(false);
                this.connectionMap.put(databaseIdentification, connection);
            } catch (SQLException ex) {
                throw new CannotGetJdbcConnectionException("Could bot get JDBC otherConnection", ex);
            }
        }
        return this.connectionMap.get(databaseIdentification);
    }

    @Override
    public void commit() throws SQLException {
        for (Connection connection : connectionMap.values()) {
            if (!autoCommit) {
                connection.commit();
            }
        }
    }

    @Override
    public void rollback() throws SQLException {
        for (Connection connection : connectionMap.values()) {
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        for (Connection connection : connectionMap.values()) {
            DataSourceUtils.releaseConnection(connection, this.dataSource);
        }
    }

    @Override
    public Integer getTimeout() throws SQLException {
        return null;
    }
}
