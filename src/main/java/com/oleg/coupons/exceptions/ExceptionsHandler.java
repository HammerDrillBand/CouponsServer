package com.oleg.coupons.exceptions;

import com.oleg.coupons.dto.ErrorBean;
import com.oleg.coupons.enums.ErrorType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;


@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler
    @ResponseBody
    public ErrorBean toResponse(Throwable throwable, HttpServletResponse httpServletResponse) {
        if (throwable instanceof ApplicationException) {
            ApplicationException appException = (ApplicationException) throwable;
            if (appException.getErrorType().isShowStackTrace()) {
                appException.printStackTrace();
            }

            ErrorType errorType = appException.getErrorType();
            int errorNumber = errorType.getErrorNum();
            String errorMessage = errorType.getErrorMessage();
            ErrorBean errorBean = new ErrorBean(errorNumber, errorMessage);
            httpServletResponse.setStatus(errorNumber);
            return errorBean;
        }

        throwable.printStackTrace();

        ErrorBean errorBean = new ErrorBean(ErrorType.GENERAL_ERROR);
        return errorBean;
    }
}

