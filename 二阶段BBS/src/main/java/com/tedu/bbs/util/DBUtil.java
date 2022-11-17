package com.tedu.bbs.util;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    //Druid是阿里提供的数据库连接池。连接池的主要作用:1,控制连接数量  2,重用连接
    private static DruidDataSource dds;

    static {
        dds = new DruidDataSource();
        dds.setUsername("root");
        dds.setPassword("root");
        dds.setUrl("jdbc:mysql://localhost:3306/bbsdb?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true");
        dds.setInitialSize(5);
        dds.setMaxActive(10);
    }

    public static Connection getConnection() throws SQLException {
        return dds.getConnection();
    }

}
