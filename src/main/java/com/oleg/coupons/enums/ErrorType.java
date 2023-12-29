package com.oleg.coupons.enums;

public enum ErrorType {
    GENERAL_ERROR(800, "An error has occurred while running", true),
    COULD_NOT_FIND(801, "Could not find requested item/s", false),
    UNAUTHORIZED_REQUEST(802, "You are unauthorized to perform this task", false),

    USER_PASSWORD_INVALID(810, "The password entered is invalid", false),
    USER_TYPE_NULL(811, "user type is not defined", false),
    USERNAME_INVALID(812, "The username entered is invalid", false),
    USER_TYPE_NOT_COMPANY_BUT_COMPANY_ID_EXIST(813, "This user type cannot have a company ID", false),
    USER_TYPE_COMPANY_BUT_COMPANY_ID_NOT_EXIST(814, "This user must be assigned to a company", false),
    LOGIN_FAILED(815, "Username or password are incorrect", false),

    COUPON_DOES_NOT_EXIST(820, "The coupon you were looking for does not exist", false),
    COUPON_AMOUNT_INVALID(821, "Coupon amount is not valid", false),
    COUPON_PRICE_INVALID(822, "Coupon price is not valid", false),
    COUPON_START_DATE_AFTER_END_DATE(823, "The coupon start date cannot be later than the end date", false),
    COUPON_START_DATE_TOO_EARLY(824, "The coupon start date is too early", false),
    COUPON_DESCRIPTION_TOO_LONG(825, "The coupon description entered is too long", false),
    COUPON_DESCRIPTION_TOO_SHORT(826, "The coupon description entered is too short", false),
    COUPON_NAME_TOO_LONG(827, "The coupon name entered is too long", false),
    COUPON_NAME_TOO_SHORT(828, "The coupon name entered is too short", false),
    COUPON_NOT_AVAILABLE(829, "This coupon is not available for purchase", false),

    COMPANY_NOT_EXIST(830, "Company does not exist", false),
    COMPANY_TYPE_NULL(831, "Company type is not defined", false),
    COMPANY_REGISTRY_NUMBER_INVALID(832, "The registry number entered is invalid", false),
    COMPANY_ADDRESS_TOO_LONG(833, "The company address entered is too long", false),
    COMPANY_ADDRESS_TOO_SHORT(834, "The company address entered is too short", false),
    COMPANY_NAME_TOO_LONG(835, "The company name entered is too long", false),
    COMPANY_NAME_TOO_SHORT(835, "The company name entered is too short", false),
    COMPANY_EMAIL_INVALID(836, "The company contact email entered is invalid", false),

    PURCHASE_AMOUNT_NOT_VALID(840, "Purchase amount cannot be negative or larger than the available amount", false),
    PURCHASE_DATE_BEFORE_COUPON_START_DATE(841, "The purchase date cannot be before the coupon start date", false),
    PURCHASE_DATE_AFTER_COUPON_END_DATE(842, "The purchase date cannot be after the coupon end date", false),
    DECREASED_AMOUNT_IS_TOO_LARGE(843, "The amount you required to reduce from the coupon is too large", false),

    CATEGORY_DOES_NOT_EXIST(850, "Coupon category does not exist", false),
    CATEGORY_NAME_TOO_LONG(851, "The category name entered is too long", false),
    CATEGORY_NAME_TOO_SHORT(852, "The category name entered is too short", false);

    private int errorNum;
    private String errorMessage;
    private boolean isShowStackTrace;

    ErrorType(int errorNum, String errorMessage, boolean isShowStackTrace) {
        this.errorNum = errorNum;
        this.errorMessage = errorMessage;
        this.isShowStackTrace = isShowStackTrace;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isShowStackTrace() {
        return isShowStackTrace;
    }

    public void setShowStackTrace(boolean showStackTrace) {
        isShowStackTrace = showStackTrace;
    }
}
