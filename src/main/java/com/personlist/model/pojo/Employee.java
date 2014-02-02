package com.personlist.model.pojo;

import com.personlist.model.PersonListEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * pojo container class for Employee
 */
public class Employee implements PersonListEntity,Serializable {

    private int id;
    private String surname;
    private String name;
    private String secondName;
    private Date dateOfBirth;
    private Date hireDate;
    private Role role;
    private ManagerInfo managerInfo;
    private String employeeDescription;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ManagerInfo getManagerInfo() {
        return managerInfo;
    }

    public void setManagerInfo(ManagerInfo managerInfo) {
        this.managerInfo = managerInfo;
    }

    public String getEmployeeDescription() {
        return employeeDescription;
    }

    public void setEmployeeDescription(String employeeDescription) {
        this.employeeDescription = employeeDescription;
    }

    public static class EmployeeBuilder {
        private int id;
        private String surname;
        private String name;
        private String secondName;
        private Date dateOfBirth;
        private Date hireDate;
        private Role role;
        private ManagerInfo managerInfo;
        private String employeeDescription;

        public EmployeeBuilder(int id) {
            this.id = id;
        }

        public EmployeeBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public EmployeeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EmployeeBuilder secondName(String secondName) {
            this.secondName = secondName;
            return this;
        }


        public EmployeeBuilder dateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public EmployeeBuilder hireDate(Date hireDate) {
            this.hireDate = hireDate;
            return this;
        }

        public EmployeeBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public EmployeeBuilder managerInfo(ManagerInfo managerInfo) {
            this.managerInfo = managerInfo;
            return this;
        }

        public EmployeeBuilder employeeDescription(String employeeDescription) {
            this.employeeDescription = employeeDescription;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }

    }
    public Employee(){

    }
    private Employee(EmployeeBuilder builder) {
        this.id = builder.id;
        this.surname = builder.surname;
        this.name = builder.name;
        this.secondName = builder.secondName;
        this.dateOfBirth = builder.dateOfBirth;
        this.hireDate = builder.hireDate;
        this.managerInfo = builder.managerInfo;
        this.role = builder.role;
        this.employeeDescription = builder.employeeDescription;
    }


}
