package com.personlist.model.pojo;

import com.personlist.model.PersonListEntity;

/**
 * Created with IntelliJ IDEA.
 * User: DAP
 * Date: 27.01.14
 * Time: 21:12
 * To change this template use File | Settings | File Templates.
 */
public class EmployeeDescription implements PersonListEntity {

    private int id;
    private String description;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EmployeeDescription(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public EmployeeDescription(){

    }
}
