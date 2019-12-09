package com.cyberiansoft.test.enums;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    FIRST_NAME_IS_REQUIRED("First Name is required"),
    LAST_NAME_IS_REQUIRED("Last Name is required"),
    EMAIL_IS_REQUIRED("Email is required"),
    EMAIL_IS_INVALID("Email is not valid!"),
    PLEASE_ENTER_PASSWORD("Please enter your password!"),
    PASSWORD_SHOULD_BE_LONGER("Password should be longer than 5 symbols!"),
    PLEASE_CONFIRM_PASSWORD("Please confirm password!");

    private String errorMessage;

    ErrorMessages(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
