package com.personlist.comparator;

import com.personlist.model.pojo.Employee;

import java.util.Comparator;

/**
 * class that representing date comparator
 */
public class HireDateComparator implements Comparator<Employee>{
    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getHireDate().compareTo(o2.getHireDate());
    }
}
