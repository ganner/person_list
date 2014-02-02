package com.personlist.model.pojo;

/**
 * pojo container class form manager info
 */
public class ManagerInfo {
    public String surname;
    public Integer id;

    public ManagerInfo(int id,String surname) {
        this.id = id;
        this.surname = surname;
    }

    public ManagerInfo() {
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
