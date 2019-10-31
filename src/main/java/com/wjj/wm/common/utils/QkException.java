package com.wjj.wm.common.utils;

/**
 * @ClassName QkException
 * @Description 自定义全局异常
 * @Author weng_jun_ji_
 * @Date 2019/9/29 15:05
 * @Vervsion 1.0
 */
public class QkException extends RuntimeException {

    private int code;
    private String message;

    public QkException() {
        super();
    }

    public QkException(String message) {
        super(message);
    }

    public QkException(Throwable cause) {
        super(cause);
    }

    public QkException(String message, Throwable cause) {
        super(message, cause);
    }

    public QkException(String message, int code) {
        this.code = code;
        this.message = message;
    }

}
