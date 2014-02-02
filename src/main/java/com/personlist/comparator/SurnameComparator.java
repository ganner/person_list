package com.personlist.comparator;

import com.personlist.model.pojo.Employee;

import java.util.Comparator;

/**
 * class that representing surname comparator
 */
public class SurnameComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getSurname().compareToIgnoreCase(o2.getSurname());
    }
}
