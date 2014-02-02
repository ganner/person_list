package com.personlist.controller;

import com.personlist.dao.EmployeesDao;
import com.personlist.model.pojo.Employee;
import com.personlist.xmlprocessing.XmlWorker;

import javax.naming.NamingException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by DAP on 02.02.14.
 */
public class EmployeeManipulator {

    public static void getEmployeesFromXmlStreamAndInsertToDB(InputStream xmlStream) throws SQLException, NamingException {
        List<Employee> listOfEmployee = XmlWorker.fromXML(Employee.class, xmlStream, XmlWorker.createEmployeeAliasXStream());
        EmployeesDao.getInstance().insertAll(listOfEmployee);
    }
}
