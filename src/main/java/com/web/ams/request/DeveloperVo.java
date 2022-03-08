package com.web.ams.request;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DeveloperVo implements Serializable {
    public final static Long SERIAL_ID = 1L;

    @NotBlank(message = "Developer Name Should not be null")
    private String name;
    @NotBlank(message = "Phone Number Should not be null")
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
