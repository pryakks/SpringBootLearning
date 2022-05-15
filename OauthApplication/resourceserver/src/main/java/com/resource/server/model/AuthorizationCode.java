package com.resource.server.model;

public class AuthorizationCode {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AuthorizationCode(String code) {
               this.code = code;
    }

    @Override
    public String toString() {
        return "AuthorizationCode{" +
                "code='" + code + '\'' +
                '}';
    }
}
