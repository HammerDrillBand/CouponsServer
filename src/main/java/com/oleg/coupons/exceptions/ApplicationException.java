package com.oleg.coupons.exceptions;

import com.oleg.coupons.enums.ErrorType;

public class ApplicationException extends Exception {
    private ErrorType errorType;

    // Only for user input validations
    public ApplicationException(ErrorType errorType) {
        super(errorType.getErrorMessage());
        this.errorType = errorType;
    }

    //Ctor for programmer
    public ApplicationException(ErrorType errorType, String errorMessage) {
        super(errorMessage);
        this.errorType = errorType;
        errorType.setErrorMessage(errorMessage);
    }

    // Ctor used when wrapping another exception of a 3rd party
    public ApplicationException(ErrorType errorType, String errorMessage, Exception innerException) {
        super(errorMessage, innerException);
        this.errorType = errorType;
        errorType.setErrorMessage(errorMessage);
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
