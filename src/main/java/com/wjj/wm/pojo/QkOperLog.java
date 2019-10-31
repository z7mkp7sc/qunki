package com.wjj.wm.pojo;

import java.util.Date;

public class QkOperLog {
    private String id;

    private Byte opType;

    private String opContent;

    private String operator;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Byte getOpType() {
        return opType;
    }

    public void setOpType(Byte opType) {
        this.opType = opType;
    }

    public String getOpContent() {
        return opContent;
    }

    public void setOpContent(String opContent) {
        this.opContent = opContent == null ? null : opContent.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}