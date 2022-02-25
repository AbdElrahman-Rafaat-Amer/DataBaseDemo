package com.abdo.databasedemo;

public class DTO {
    private String message;
    private String number;

    public DTO(String message, String number) {
        this.message = message;
        this.number = number;
    }

    public DTO() {
    }

    public DTO(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
