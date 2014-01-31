package com.personlist.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: DAP
 * Date: 25.01.14
 * Time: 1:16
 * To change this template use File | Settings | File Templates.
 */
public class DBConnector {

    private static volatile DBConnector dbConnector;

    private DBConnector() {
    }

    public static synchronized DBConnector getInstance() throws NamingException {
        if (dbConnector == null) {
            dbConnector = new DBConnector();
        }
        return dbConnector;
    }

    public Connection getConnection() throws NamingException, SQLException {
        Context context = new InitialContext();
        DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/mysqlDS");
        context.close();
        return ds.getConnection();

    }
}
