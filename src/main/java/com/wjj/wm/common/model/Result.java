package com.wjj.wm.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 统一结果封装
 * @Param
 * @Return
 **/
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Result<T> implements Serializable {

    /**
     * 成功码.
     */
    public static final int SUCCESS_CODE = 200;
    /**
     * 成功信息.
     */
    public static final String SUCCESS_MESSAGE = "操作成功";
    /**
     * 错误码.
     */
    public static final int ERROR_CODE = 500;
    /**
     * 错误信息.
     */
    public static final String ERROR_MESSAGE = "内部异常";
    /**
     * 错误码：参数非法
     */
    public static final int ILLEGAL_ARGUMENT_CODE = 100;
    /**
     * 错误信息：参数非法
     */
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "参数非法";
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 4893280118017319089L;
    /**
     * 编号.
     */
    private int code;

    /**
     * 信息.
     */
    private String message;

    /**
     * 结果数据
     */
    private T data;

    /**
     * Instantiates a new wrapper. default code=200
     */
    public Result() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    /**
     * Instantiates a new wrapper.
     *
     * @param code    the code
     * @param message the message
     */
    public Result(int code, String message) {
        this(code, message, null);
    }

    /**
     * Instantiates a new wrapper.
     *
     * @param code    the code
     * @param message the message
     * @param data    the result
     */
    public Result(int code, String message, T data) {
        super();
        this.code(code).message(message).data(data);
    }

    /**
     * Sets the 编号 , 返回自身的引用.
     *
     * @param code the new 编号
     * @return the wrapper
     */
    private Result<T> code(int code) {
        this.setCode(code);
        return this;
    }

    /**
     * Sets the 信息 , 返回自身的引用.
     *
     * @param message the new 信息
     * @return the wrapper
     */
    private Result<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * Sets the 结果数据 , 返回自身的引用.
     *
     * @param data the new 结果数据
     * @return the wrapper
     */
    public Result<T> data(T data) {
        this.setData(data);
        return this;
    }

    /**
     * 判断是否成功： 依据 Result.SUCCESS_CODE == this.code
     *
     * @return code =200,true;否则 false.
     */
    @JsonIgnore
    public boolean success() {
        return Result.SUCCESS_CODE == this.code;
    }

    /**
     * 判断是否成功： 依据 Result.SUCCESS_CODE != this.code
     *
     * @return code !=200,true;否则 false.
     */
    @JsonIgnore
    public boolean error() {
        return !success();
    }

}

