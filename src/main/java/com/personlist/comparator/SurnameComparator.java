package com.personlist.comparator;

import com.personlist.model.pojo.Employee;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: DAP
 * Date: 29.01.14
 * Time: 0:25
 * To change this template use File | Settings | File Templates.
 */
public class SurnameComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getSurname().compareToIgnoreCase(o2.getSurname());
    }
}
