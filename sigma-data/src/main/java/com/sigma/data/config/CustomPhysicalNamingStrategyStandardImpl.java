package com.sigma.data.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * @author huston.peng
 * @version 1.0.5
 * date-time: 2019/5/19-15:52
 * desc: 保持表名
 **/
public class CustomPhysicalNamingStrategyStandardImpl extends PhysicalNamingStrategyStandardImpl {
    private static final long serialVersionUID = 1383021413247872469L;

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return name;
    }
}
