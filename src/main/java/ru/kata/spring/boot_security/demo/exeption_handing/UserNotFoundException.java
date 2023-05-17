package ru.kata.spring.boot_security.demo.exeption_handing;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
