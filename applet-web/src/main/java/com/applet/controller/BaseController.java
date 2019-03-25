package com.applet.controller;

import com.applet.common.result.ResultModel;
import com.applet.service.member.TokenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用，比如：获取用户信息。检查用户是否存在，异常处理
 */
public class BaseController {
    private static final Logger logger = LogManager.getLogger(BaseController.class);

    @Autowired
    private TokenService tokenService;

    /**
     * 获取用户id
     *
     * @param request
     * @return
     */
    public Long getMemberId(HttpServletRequest request) {
        if(request == null) return null;
        String token = request.getHeader("token");
        return tokenService.getMemberId(token);
    }

    /**
     * Requst data format is invalid
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Object handleMessageNotReadableException(HttpMessageNotReadableException ex,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {

        if (ex instanceof HttpMessageNotReadableException) {
            return ResultModel.fail("请求数据格式异常");
        }

        ex.printStackTrace();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        logger.error("Requst data 发生异常，开始保存到数据");
//        ExceptionLogHelper.saveLog(ex, request);
        logger.error("Requst data 发生异常，保存到数据成功");

        return ResultModel.fail("出错啦，请重试");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {

        // 过滤重复主键异常
        if (ex instanceof DuplicateKeyException) {
            return ResultModel.fail("有重复主键");
        }
        if (ex instanceof MissingServletRequestParameterException) {
            return ResultModel.fail("缺少请求参数");
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return ResultModel.fail("方法参数类型不匹配");
        }
        if (ex instanceof BadSqlGrammarException) {
            return ResultModel.fail("SQL语法异常");
        }
        if (ex instanceof HttpMediaTypeNotSupportedException) {
            return ResultModel.fail("不支持当前媒体类型");
        }

        ex.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        logger.error("发生异常，开始保存到数据");
//        ExceptionLogHelper.saveLog(ex, request);
        logger.error("发生异常，保存到数据成功");

        return ResultModel.fail("出错啦，请重试");
    }


}
