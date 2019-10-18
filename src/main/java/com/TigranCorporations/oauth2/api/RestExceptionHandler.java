package com.TigranCorporations.oauth2.api;

import com.TigranCorporations.oauth2.core.ex.TokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class RestExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleTokenException(TokenException ex, HttpServletRequest req, HttpServletResponse resp){
        log.error("Internal token exception:{}{}{}",ex.getMessage(),System.lineSeparator(),requestToString(req));
        resp.setStatus(HttpStatus.FORBIDDEN.value());
    }

    private String requestToString(HttpServletRequest request) {
        return String.format("%s %s", request.getMethod(), request.getRequestURL());
    }
}
