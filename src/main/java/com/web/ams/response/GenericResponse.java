package com.web.ams.response;

import java.io.Serializable;
import java.util.List;

public class GenericResponse implements Serializable {
    public final static Long SERIAL_ID = 1L;

    private String status;
    private Integer statusCode;
    private String teamId;
    private List<RequestValidations> requestValidationsList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public List<RequestValidations> getRequestValidationsList() {
        return requestValidationsList;
    }

    public void setRequestValidationsList(List<RequestValidations> requestValidationsList) {
        this.requestValidationsList = requestValidationsList;
    }

}
