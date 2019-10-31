package com.wjj.wm.common.utils;

import com.wjj.wm.common.model.Result;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: liyue
 * 返回结果包装类
 */
public class ResultUtil {

    /**
     * Instantiates a new ResultUtils.
     */
    private ResultUtil() {
    }

    /**
     * Wrap.
     *
     * @param <E>     the element type
     * @param code    the code
     * @param message the message
     * @param o       the o
     * @return the Result
     */
    public static <E> Result<E> wrap(int code, String message, E o) {
        return new Result<E>(code, message, o);
    }

    /**
     * Wrap.
     *
     * @param <E>     the element type
     * @param code    the code
     * @param message the message
     * @return the Result
     */
    public static <E> Result<E> wrap(int code, String message) {
        return wrap(code, message, null);
    }

    /**
     * Wrap.
     *
     * @param <E>  the element type
     * @param code the code
     * @return the Result
     */
    public static <E> Result<E> wrap(int code) {
        return wrap(code, null);
    }

    /**
     * Wrap.
     *
     * @param <E> the element type
     * @param e   the e
     * @return the Result
     */
    public static <E> Result<E> wrap(Exception e) {
        return new Result<>(Result.ERROR_CODE, e.getMessage());
    }

    /**
     * Un Result.
     *
     * @param <E>     the element type
     * @param Result the Result
     * @return the e
     */
    public static <E> E unWrap(Result<E> Result) {
        return Result.getData();
    }

    /**
     * Wrap ERROR. code=100
     *
     * @param <E> the element type
     * @return the Result
     */
    public static <E> Result<E> illegalArgument() {
        return wrap(Result.ILLEGAL_ARGUMENT_CODE, Result.ILLEGAL_ARGUMENT_MESSAGE);
    }

    /**
     * Wrap ERROR. code=500
     *
     * @param <E> the element type
     * @return the Result
     */
    public static <E> Result<E> error() {
        return wrap(Result.ERROR_CODE, Result.ERROR_MESSAGE);
    }


    /**
     * Error Result.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the Result
     */
    public static <E> Result<E> error(String message) {
        return wrap(Result.ERROR_CODE, StringUtils.isBlank(message) ? Result.ERROR_MESSAGE : message);
    }

    /**
     * Wrap SUCCESS. code=200
     *
     * @param <E> the element type
     * @return the Result
     */
    public static <E> Result<E> ok() {
        return new Result<>();
    }

    /**
     * Ok Result.
     *
     * @param <E> the type parameter
     * @param o   the o
     * @return the Result
     */
    public static <E> Result<E> ok(E o) {
        return new Result<>(Result.SUCCESS_CODE, Result.SUCCESS_MESSAGE, o);
    }
}

