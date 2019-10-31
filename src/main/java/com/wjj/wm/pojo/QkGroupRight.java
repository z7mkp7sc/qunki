package com.wjj.wm.pojo;

public class QkGroupRight {
    private String id;

    private String groupId;

    private String rightId;

    private Byte rightType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
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