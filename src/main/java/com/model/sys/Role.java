package com.model.sys;

import com.model.page.PageDto;

public class Role extends PageDto{
	String name;
	String useable;
    String rolekey;
    String description;

    public String getRolekey() {
        return rolekey;
    }

    public void setRolekey(String rolekey) {
        this.rolekey = rolekey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUseable() {
		return useable;
	}
	public void setUseable(String useable) {
		this.useable = useable;
	}

}
