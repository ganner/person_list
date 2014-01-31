package com.personlist.dao;


import com.personlist.SQLQueries;
import com.personlist.model.pojo.Role;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: DAP
 * Date: 27.01.14
 * Time: 1:59
 * To change this template use File | Settings | File Templates.
 */
public class RolesDao implements PersonalListDao<Role>{

    private static RolesDao instance;

    private RolesDao(){}

    public static synchronized RolesDao getInstance(){
        if(instance == null){
            instance = new RolesDao();
        }
        return instance;
    }

    public void insertOne(Role employee) throws NamingException, SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void deleteOne(Role employee) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateOne(Role employee) throws NamingException, SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public List<Role> getAll() throws NamingException, SQLException {
        List<Role> listOfRoles = new ArrayList<Role>();
        Connection connection = DBConnector.getInstance().getConnection();
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(SQLQueries.GET_ALL_ROLES);
        while (resultSet.next()){
            String roleName = resultSet.getString("role_name");
            int roleId =  resultSet.getInt("r_ID");

            listOfRoles.add(new Role(roleName,roleId));
        }
        connection.close();
        return listOfRoles;
    }


}
