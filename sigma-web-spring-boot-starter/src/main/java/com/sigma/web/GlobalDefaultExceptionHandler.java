package com.sigma.web;

import com.sigma.sigmacore.exception.SigmaException;
import com.sigma.sigmacore.web.SigmaResponse;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-14:23
 * desc: 全局異常處理
 **/
@Slf4j
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @Autowired
    MessageSource messageSource;

    /**
     * 未知異常處理
     *
     * @param request 請求
     * @param ex      異常
     * @return 返回
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public SigmaResponse handleException(HttpServletRequest request, Exception ex) {
        var message = MessageFormat.format("url:{0},method:{1},exception:{2}",
                request.getRequestURI(),
                request.getMethod(),
                ex.getMessage() + Arrays.toString(ex.getStackTrace()));

        log.error("未知異常{}", message);

        return SigmaResponse.builder()
                .withCode("500")
                .withMessage("Server error.")
                .build();
    }

    /**
     * 参数校验异常
     * 注：SpringBoot的Web组件内部集成了hibernate-validator，所以不需要额外导入JSR303包
     *
     * @param request 请求
     * @param e       异常
     * @return SigmaResponse
     * @throws Exception 异常
     */
    @ExceptionHandler(
            value = {MethodArgumentNotValidException.class,
                    BindException.class,
                    HttpMessageNotReadableException.class
            }
    )
    @ResponseBody
    public SigmaResponse methodArgumentNotValidHandler(HttpServletRequest request, Exception e) throws Exception {

        log.error("验证异常：", e);

        List<FieldError> fieldErrors = null;
        if (e instanceof BindException) {
            //获取错误字段集合
            fieldErrors = ((BindException) e).getBindingResult().getFieldErrors();
        } else if (e instanceof MethodArgumentNotValidException) {
            fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        }

        var tip = "";
        //获取本地locale,zh_CN
        Locale currentLocale = LocaleContextHolder.getLocale();
        //遍历错误字段获取错误消息
        if (CollectionUtils.isEmpty(fieldErrors)) {
            tip = "Paramters invalid！" + e.getMessage();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : fieldErrors) {
                //获取错误信息
                String errorMessage = messageSource.getMessage(fieldError, currentLocale);
                //添加到错误消息集合内
                stringBuilder.append(fieldError.getField()).append("：").append(errorMessage).append(", ");
            }
            tip = stringBuilder.toString();
        }

        return SigmaResponse.create("00000001", tip);
    }


    @ExceptionHandler({SigmaException.class})
    @ResponseBody
    public SigmaResponse handleSigmaException(SigmaException ex) {

        log.error("运行时异常：", ex);

        //默认设置为code
        String msg = ex.getErrorCode();
        try {
            msg = messageSource.getMessage(ex.getErrorCode(), ex.getArguments(),
                    LocaleContextHolder.getLocale());
        } catch (Exception ex2) {
            log.error("读取异常代码失败！", ex2);
        }

        return SigmaResponse.builder()
                .withCode(ex.getErrorCode())
                .withMessage(msg)
                .build();
    }
}
