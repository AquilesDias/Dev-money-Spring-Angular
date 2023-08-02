package com.github.aquiles.devmoneyapi.exception;

import com.github.aquiles.devmoneyapi.resource.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdviceException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrors handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        List<String> error = ex
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(errors -> errors.getDefaultMessage())
                .collect(Collectors.toList());

        return new ApiErrors(error);
    }
}
