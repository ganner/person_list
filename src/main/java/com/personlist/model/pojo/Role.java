package com.personlist.model.pojo;

import com.personlist.model.PersonListEntity;

/**
 * pojo container class for Role
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
