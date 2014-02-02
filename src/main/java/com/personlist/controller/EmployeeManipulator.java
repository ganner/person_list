package com.personlist.controller;

import com.personlist.dao.EmployeesDao;
import com.personlist.model.pojo.Employee;
import com.personlist.xmlprocessing.XmlWorker;
import javax.naming.NamingException;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller class for manipulate i/o employee data
 */
public class EmployeeManipulator {

    private static EmployeeManipulator instance;

    private EmployeeManipulator(){

    }

    public static EmployeeManipulator getInstance(){
        if(instance == null){
            instance = new EmployeeManipulator();
        }
        return instance;
    }

    /**
     * method get employees from well-formed xml document and write them to DB
     * @param xmlStream is a stream of input xml file
     * @throws SQLException
     * @throws NamingException
     */
    public void getEmployeesFromXmlStreamAndInsertToDB(InputStream xmlStream) throws SQLException, NamingException {
        List<Employee> listOfEmployee = XmlWorker.getInstance().fromXML(Employee.class, xmlStream, XmlWorker.getInstance().createEmployeeAliasXStream());
        EmployeesDao.getInstance().insertAll(listOfEmployee);
    }

    /**
     * method get all employee data from DB and write it to file
     * @param file is a file on server which contain employee data
     * @throws SQLException
     * @throws NamingException
     */
    public void saveAllEmployeesToXml(File file) throws SQLException, NamingException {
        List<Employee> listOfEmployee = EmployeesDao.getInstance().getAll();
        XmlWorker.getInstance().toXML(listOfEmployee,file,XmlWorker.getInstance().createEmployeeAliasXStream());
    }
}
