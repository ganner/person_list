package com.personlist;

/**
 * Created with IntelliJ IDEA.
 * User: DAP
 * Date: 26.01.14
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class SQLQueries {

    public static final String GET_ALL_EMPLOYEES = "SELECT e.e_ID , e.surname , e.name ,e.secondName ,e.date_of_birth ,e.hire_date , \n" +
            "            \n" +
            "            man.e_ID AS managerId , man.surname AS managerSurname, r.r_ID, r.role_name, ed_ID, ed.employee_description \n" +
            "            \n" +
            "            FROM personlistdb.employees AS e \n" +
            "                        LEFT JOIN personlistdb.employees AS man\n" +
            "                        on e.managerID = man.e_ID\n" +
            "                        LEFT JOIN personlistdb.roles AS r \n" +
            "                        on e.roleID =  r.r_ID\n" +
            "                        LEFT JOIN personlistdb.employee_descriptions AS ed\n" +
            "                        on ed.em_ID = e.e_ID\n" +
            "                        ;";

    public static final String GET_ALL_MANAGERS = "SELECT * from personlistdb.employees AS e\n" +
            "where e.roleID = (select roles.r_ID from roles where roles.role_name = 'manager' limit 1)";


    public static final String GET_ALL_ROLES = "SELECT * from personlistdb.roles";


}
