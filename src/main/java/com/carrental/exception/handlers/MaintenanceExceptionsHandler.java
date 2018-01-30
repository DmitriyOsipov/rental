package com.carrental.exception.handlers;

import com.carrental.domain.Response;
import com.carrental.domain.Status;
import com.carrental.exception.MaintenanceAlreadyExistsException;
import com.carrental.exception.MaintenanceException;
import com.carrental.exception.MaintenanceInvalidException;
import com.carrental.exception.MaintenanceNotFoundException;
import com.carrental.exception.handlers.utils.ExceptionHandlerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = {"com.carrental.controller"})
public class MaintenanceExceptionsHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private ExceptionHandlerUtil utils;

  @ExceptionHandler(MaintenanceException.class)
  protected ResponseEntity handleExceptions(MaintenanceException ex, WebRequest request) {
    Response response = new Response(Status.MAINTENANCE_ERROR);
    return utils.create(ex, request, response);
  }

  @ExceptionHandler(MaintenanceAlreadyExistsException.class)
  protected ResponseEntity handleExistsExceptions(MaintenanceAlreadyExistsException ex,
      WebRequest request) {
    Response response = new Response(Status.MAINTENANCE_EXISTS);
    return utils.create(ex, request, response);
  }

  @ExceptionHandler(MaintenanceNotFoundException.class)
  protected ResponseEntity handleNotFoundExceptions(MaintenanceNotFoundException ex,
      WebRequest request) {
    Response response = new Response(Status.MAINTENANCE_NOT_FOUND);
    return utils.create(ex, request, response);
  }

  @ExceptionHandler(MaintenanceInvalidException.class)
  protected ResponseEntity handleWrongExceptions(MaintenanceInvalidException ex,
      WebRequest request) {
    Response response = new Response(Status.MAINTENANCE_WRONG);
    return utils.create(ex, request, response);
  }
}
