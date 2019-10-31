package com.wjj.wm.pojo;

public class QkRoleRight {
    private String id;

    private String roleId;

    private String rightId;

    private Byte rightType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getRightId() {
        return rightId;
    }

    public void setRightId(String rightId) {
        this.rightId = rightId == null ? null : rightId.trim();
    }

    public Byte getRightType() {
        return rightType;
    }

    public void setRightType(Byte rightType) {
        this.rightType = rightType;
    }
}