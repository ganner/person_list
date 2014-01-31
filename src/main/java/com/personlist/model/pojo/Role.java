package com.personlist.model.pojo;

import com.personlist.model.PersonListEntity;

/**
 * Created with IntelliJ IDEA.
 * User: DAP
 * Date: 27.01.14
 * Time: 2:00
 * To change this template use File | Settings | File Templates.
 */
public class Role implements PersonListEntity {
    public String roleDescription;
    public int id;

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    @Override
    public void setId(int roleID) {
        this.id = roleID;
    }

    @Override
    public int getId() {
        return id;
    }

    public Role(String roleDescription, int roleID) {
        this.roleDescription = roleDescription;
        this.id = roleID;
    }


}
