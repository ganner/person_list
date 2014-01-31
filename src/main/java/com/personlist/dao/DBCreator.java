package com.personlist.dao;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: DAP
 * Date: 25.01.14
 * Time: 2:41
 * To change this template use File | Settings | File Templates.
 */
public class DBCreator {

    public static void createTablesIfNotExist() throws NamingException, SQLException {
        Connection connection = DBConnector.getInstance().getConnection();
        Statement st = connection.createStatement();
        st.execute("CREATE TABLE if not exists personlistdb.employee(" +
                "PersonID int," +
                "surname varchar(255)," +
                "name varchar(255)," +
                "secondName varchar(255)" +
                ");");

        connection.close();

    }

    public static void createEmployeeTable(){

    }

    public static void createManagerTable(){

    }



}
