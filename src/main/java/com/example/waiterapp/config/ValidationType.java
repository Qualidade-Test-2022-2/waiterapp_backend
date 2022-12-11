package com.example.waiterapp.config;

public enum ValidationType {

    AUTHENTICATED ("Online");
    String value;

    ValidationType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}