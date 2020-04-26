package com.insigma.webtool.util;

import star.vo.result.ResultVo;
import org.springframework.http.HttpStatus;

/**
 * 返回值工具栏
 *
 * @author toyer
 * @created 2017-12-04
 */
public class ResultUtil {
    private ResultUtil(){}

    public static ResultVo<String> returnSimpleFailed(Exception e) {
        ResultVo<String> ret = new ResultVo<>();
        ret.setSuccess(false);
        ret.setCode(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
        ret.setResultDes(e.getMessage());
        return ret;
    }

    public static ResultVo<String> returnSimpleFailed(String failedMessage) {
        ResultVo<String> ret = new ResultVo<>();
        ret.setSuccess(false);
        ret.setCode(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
        ret.setResultDes(failedMessage);
        return ret;
    }

    public static ResultVo<String> returnSimpleSuccess() {
        ResultVo<String> ret = new ResultVo<>();
        ret.setCode(String.valueOf(HttpStatus.OK.value()));
        ret.setSuccess(true);
        return ret;
    }

    public static <T> ResultVo<T> returnSimpleSuccess(T t) {
        ResultVo<T> ret = new ResultVo<>();
        ret.setCode(String.valueOf(HttpStatus.OK.value()));
        ret.setSuccess(true);
        ret.setResult(t);
        return ret;
    }
}
