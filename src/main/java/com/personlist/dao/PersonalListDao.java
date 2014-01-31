package com.personlist.dao;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: DAP
 * Date: 26.01.14
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
public interface PersonalListDao<T> {

    public void insertOne(T employee) throws NamingException, SQLException;

    public void deleteOne(T employee) throws NamingException, SQLException;

    public void updateOne(T employee) throws NamingException, SQLException;

    public List<T> getAll() throws NamingException, SQLException;
}
