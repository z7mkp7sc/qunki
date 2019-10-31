package com.wjj.wm.pojo;

public class QkUserRight {
    private String id;

    private String userId;

    private String rightId;

    private Byte rightType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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