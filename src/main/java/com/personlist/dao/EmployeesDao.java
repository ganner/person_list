package com.personlist.dao;


import com.personlist.SQLQueries;
import com.personlist.model.pojo.Employee;
import com.personlist.model.pojo.ManagerInfo;
import com.personlist.model.pojo.Role;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DAO class for employees table processing
 */
public class EmployeesDao implements PersonalListDao<Employee> {

    public static final String EMPLOYEE_TABLE = "personlistdb.employees";
    public static final String INSERT_EMPLOYEE_PROCEDURE = "insert_employee_sp";

    private EmployeesDao() {

    }

    private static EmployeesDao instance;

    public static synchronized EmployeesDao getInstance() {
        if (instance == null) {
            instance = new EmployeesDao();
        }
        return instance;
    }

    /**
     * @param employee employee an employee , you need to insert
     * @throws NamingException
     * @throws SQLException
     */
    @Override
    public void insertOne(Employee employee) throws NamingException, SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(employee);
        stringBuilder.append("call ");
        stringBuilder.append(INSERT_EMPLOYEE_PROCEDURE);
        stringBuilder.append("(");
        stringBuilder.append("?, ?, ?, ?, ?, ?, ?, ?");
        stringBuilder.append(" );");
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, employee.getSurname());
        preparedStatement.setString(2, employee.getName());
        preparedStatement.setString(3, employee.getSecondName());
        preparedStatement.setDate(4, new java.sql.Date(employee.getDateOfBirth().getTime()));
        preparedStatement.setDate(5, new java.sql.Date(employee.getHireDate().getTime()));
        if (employee.getManagerInfo() != null) {
            preparedStatement.setInt(6, employee.getManagerInfo().getId());
        } else {
            preparedStatement.setNull(6, Types.INTEGER);
        }
        preparedStatement.setInt(7, employee.getRole().getId());
        if (employee.getEmployeeDescription() != null) {
            preparedStatement.setString(8, employee.getEmployeeDescription());
        } else {
            preparedStatement.setNull(8, Types.VARCHAR);
        }
        preparedStatement.executeUpdate();
        connection.close();
    }

    /**
     * @param employee employee an employee , you need to delete
     * @throws NamingException
     * @throws SQLException
     */
    @Override
    public void deleteOne(Employee employee) throws NamingException, SQLException {
        Connection connection = DBConnector.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE " + EMPLOYEE_TABLE +
                " SET personlistdb.employees.managerID = null WHERE personlistdb.employees.managerID = " + employee.getId() + " ;");
        statement.executeUpdate("DELETE FROM " + EMPLOYEE_TABLE + " WHERE e_ID = " + employee.getId());
        connection.close();
    }

    /**
     * @param employee an employee , you need to update
     * @throws NamingException
     * @throws SQLException
     */
    @Override
    public void updateOne(Employee employee) throws NamingException, SQLException {
        Connection connection = DBConnector.getInstance().getConnection();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("call personlistdb.update_employee_sp");
        stringBuilder.append("(");
        stringBuilder.append("?,?,?,?");
        stringBuilder.append(");");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setInt(1, employee.getId());
        preparedStatement.setInt(2, employee.getRole().getId());
        preparedStatement.setString(4, employee.getEmployeeDescription());
        if (employee.getManagerInfo().getId() == 0) {
            preparedStatement.setNull(3, Types.INTEGER);
        } else {
            preparedStatement.setNull(3, employee.getManagerInfo().getId());
        }
        preparedStatement.executeUpdate();
    }

    /**
     * @return all employees in person list
     * @throws NamingException
     * @throws SQLException
     */
    @Override
    public List<Employee> getAll() throws NamingException, SQLException {
        List<Employee> listOfEmployee = new ArrayList<>();
        Connection connection = DBConnector.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQLQueries.GET_ALL_EMPLOYEES);
        while (resultSet.next()) {
            int employeeId = resultSet.getInt("e_ID");
            String surname = resultSet.getString("surname");
            String name = resultSet.getString("name");
            String secondName = resultSet.getString("secondName");
            Date dateOfBirth = new Date(resultSet.getDate("date_of_birth").getTime());
            Date hireDate = new Date(resultSet.getDate("hire_date").getTime());
            String managerSurname = resultSet.getString("managerSurname");
            int managerId = resultSet.getInt("managerID");
            int roleID = resultSet.getInt("r_ID");
            String roleName = resultSet.getString("role_name");
            String employeeDescription = resultSet.getString("employee_description");

            Employee.EmployeeBuilder employeeBuilder = new Employee.EmployeeBuilder(employeeId);

            listOfEmployee.add(employeeBuilder.surname(surname).name(name).secondName(secondName)
                    .dateOfBirth(dateOfBirth).hireDate(hireDate)
                    .managerInfo(new ManagerInfo(managerId, managerSurname))
                    .role(new Role(roleName, roleID))
                    .employeeDescription(employeeDescription)
                    .build());
        }
        connection.close();
        return listOfEmployee;
    }

    /**
     * @return all employees that have manager role
     * @throws NamingException
     * @throws SQLException
     */
    public List<ManagerInfo> getAllManagers() throws NamingException, SQLException {
        List<ManagerInfo> listOfEmployee = new ArrayList<>();
        Connection connection = DBConnector.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQLQueries.GET_ALL_MANAGERS);
        while (resultSet.next()) {
            int id = resultSet.getInt("e_ID");
            String surname = resultSet.getString("surname");
            listOfEmployee.add(new ManagerInfo(id, surname));
        }
        connection.close();
        return listOfEmployee;
    }
}
