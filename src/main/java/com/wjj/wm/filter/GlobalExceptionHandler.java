package com.wjj.wm.filter;

import com.wjj.wm.common.model.Result;
import com.wjj.wm.common.utils.QkException;
import com.wjj.wm.common.utils.ResultUtil;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 全局统一异常处理
 * @Author weng_jun_ji_
 * @Date 2019/9/29 10:05
 * @Vervsion 1.0
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        //全局异常处理
        if (e instanceof QkException) return ResultUtil.error(((QkException) e).getMessage());

        //绑定异常明确提示给用户
        if (e instanceof BindException) {
            BindException exception = (BindException) e;
            List<ObjectError> list = exception.getAllErrors();
            String message = list.get(0).getDefaultMessage();
            return ResultUtil.error(message);
        }
        //其余异常返回服务器异常
        return ResultUtil.error("服务器异常");
    }
}
