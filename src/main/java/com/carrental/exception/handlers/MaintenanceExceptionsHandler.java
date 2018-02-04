package com.carrental.exception.handlers;

import com.carrental.domain.Response;
import com.carrental.domain.Status;
import com.carrental.exception.MaintenanceAlreadyExistsException;
import com.carrental.exception.MaintenanceException;
import com.carrental.exception.MaintenanceInvalidException;
import com.carrental.exception.MaintenanceNotFoundException;
import com.carrental.exception.handlers.utils.ExceptionHandlerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = {"com.carrental.controller"})
public class MaintenanceExceptionsHandler {

  @Autowired
  private ExceptionHandlerUtil utils;

  @ExceptionHandler(MaintenanceException.class)
  protected ModelAndView handleExceptions(MaintenanceException ex) {
    Response response = new Response(Status.MAINTENANCE_ERROR);
    return utils.create(ex, response);
  }

  @ExceptionHandler(MaintenanceAlreadyExistsException.class)
  protected ModelAndView handleExistsExceptions(MaintenanceAlreadyExistsException ex) {
    Response response = new Response(Status.MAINTENANCE_EXISTS);
    return utils.create(ex, response);
  }

  @ExceptionHandler(MaintenanceNotFoundException.class)
  protected ModelAndView handleNotFoundExceptions(MaintenanceNotFoundException ex) {
    Response response = new Response(Status.MAINTENANCE_NOT_FOUND);
    return utils.create(ex, response);
  }

  @ExceptionHandler(MaintenanceInvalidException.class)
  protected ModelAndView handleWrongExceptions(MaintenanceInvalidException ex) {
    Response response = new Response(Status.MAINTENANCE_WRONG);
    return utils.create(ex, response);
  }
}
