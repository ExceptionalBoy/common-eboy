package com.eboy.common.result;

import com.eboy.common.bean.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Header implements Serializable {

    private String code;

    private String message;

    private String serialNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date transDate;

    private User user;

    public Header() {
    }

    public Header(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
