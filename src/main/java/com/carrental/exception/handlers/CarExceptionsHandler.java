package com.carrental.exception.handlers;

import com.carrental.domain.Response;
import com.carrental.domain.Status;
import com.carrental.exception.CarAlreadyExistsException;
import com.carrental.exception.CarException;
import com.carrental.exception.CarNotFoundException;
import com.carrental.exception.handlers.utils.ExceptionHandlerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = {"com.carrental.controller"})
public class CarExceptionsHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private ExceptionHandlerUtil utils;

  @ExceptionHandler(CarException.class)
  protected ResponseEntity handleCarExceptions(CarException ex, WebRequest request) {
    Response response = new Response(Status.CAR_ERROR);
    return utils.create(ex, request, response);
  }

  @ExceptionHandler(CarAlreadyExistsException.class)
  protected ResponseEntity handleExistsExceptions(CarAlreadyExistsException ex,
      WebRequest request) {
    Response response = new Response(Status.CAR_EXISTS);
    return utils.create(ex, request, response);
  }

  @ExceptionHandler(CarNotFoundException.class)
  protected ResponseEntity handleNotFoundExceptions(CarNotFoundException ex, WebRequest request) {
    Response response = new Response(Status.CAR_NOT_FOUND);
    return utils.create(ex, request, response);
  }
}
