package com.github.aquiles.devmoneyapi.exception;

import com.github.aquiles.devmoneyapi.resource.ApiErrors;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdviceException {

    @Autowired
    MessageSource messageSourced;

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handlerDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){

        String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex);

        StandardError error = new StandardError(

                System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value(),
                "Violação de dados!",
                messageDeveloper,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PessoaInexistenteOuInativaException.class)
    public ResponseEntity<Object> handlerPessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex, HttpServletRequest request){

        String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex);

        StandardError error = new StandardError(

                System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value(),
                "Violação de dados!",
                messageDeveloper,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }
}
