package com.web.ams.response;

import java.io.Serializable;

public class RequestValidations implements Serializable {
    public final static Long SERIAL_ID = 1L;

    private String field;
    private String message;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
