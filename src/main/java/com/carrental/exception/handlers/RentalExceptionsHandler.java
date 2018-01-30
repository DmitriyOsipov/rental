package com.carrental.exception.handlers;

import com.carrental.domain.Response;
import com.carrental.domain.Status;
import com.carrental.exception.RentalAlreadyExistsException;
import com.carrental.exception.RentalException;
import com.carrental.exception.RentalInvalidException;
import com.carrental.exception.RentalNotFoundException;
import com.carrental.exception.handlers.utils.ExceptionHandlerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = {"com.carrental.controller"})
public class RentalExceptionsHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private ExceptionHandlerUtil utils;

  @ExceptionHandler(RentalException.class)
  protected ResponseEntity handleRentalExceptions(RentalException ex, WebRequest request) {
    Response response = new Response(Status.RENTAL_ERROR);
    return utils.create(ex, request, response);
  }

  @ExceptionHandler(RentalAlreadyExistsException.class)
  protected ResponseEntity handleExistsExceptions(RentalAlreadyExistsException ex,
      WebRequest request) {
    Response response = new Response(Status.RENTAL_EXISTS);
    return utils.create(ex, request, response);
  }

  @ExceptionHandler(RentalNotFoundException.class)
  protected ResponseEntity handleNotFoundExceptions(RentalNotFoundException ex,
      WebRequest request) {
    Response response = new Response(Status.RENTAL_NOT_FOUND);
    return utils.create(ex, request, response);
  }

  @ExceptionHandler(RentalInvalidException.class)
  protected ResponseEntity handleWrongExceptions(RentalInvalidException ex, WebRequest request) {
    Response response = new Response(Status.RENTAL_WRONG);
    return utils.create(ex, request, response);
  }
}
