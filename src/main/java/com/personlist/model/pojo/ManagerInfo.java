package com.personlist.model.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: DAP
 * Date: 27.01.14
 * Time: 1:16
 * To change this template use File | Settings | File Templates.
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
