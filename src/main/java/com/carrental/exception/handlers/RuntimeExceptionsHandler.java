package com.carrental.exception.handlers;

import com.carrental.domain.Response;
import com.carrental.domain.Status;
import com.carrental.exception.handlers.utils.ExceptionHandlerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = {"com.carrental.controller"})
public class RuntimeExceptionsHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private ExceptionHandlerUtil utils;

  @ExceptionHandler(RuntimeException.class)
  protected ResponseEntity handleExceptions(RuntimeException ex, WebRequest request) {
    Response response = new Response(Status.ERROR);
    return utils.create(ex, request, response);
  }

}
