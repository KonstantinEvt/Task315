package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kata.spring.boot_security.demo.exeption_handing.UserExeptionData;
import ru.kata.spring.boot_security.demo.exeption_handing.UserNotFoundException;

@ControllerAdvice
public class UserGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UserExeptionData> handleException(UserNotFoundException userNotFoundExeption) {
        UserExeptionData userExeptionData = new UserExeptionData();
        userExeptionData.setInfo(userNotFoundExeption.getMessage());
        return new ResponseEntity<>(userExeptionData, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<UserExeptionData> handleException(Exception exception) {
        UserExeptionData userExeptionData = new UserExeptionData();
        userExeptionData.setInfo(exception.getMessage());
        return new ResponseEntity<>(userExeptionData, HttpStatus.BAD_REQUEST);
    }
}
