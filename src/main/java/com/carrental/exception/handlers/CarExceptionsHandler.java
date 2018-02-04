package com.carrental.exception.handlers;

import com.carrental.domain.Response;
import com.carrental.domain.Status;
import com.carrental.exception.CarAlreadyExistsException;
import com.carrental.exception.CarException;
import com.carrental.exception.CarNotFoundException;
import com.carrental.exception.handlers.utils.ExceptionHandlerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = {"com.carrental.controller"})
public class CarExceptionsHandler {

  @Autowired
  private ExceptionHandlerUtil utils;

  @ExceptionHandler(CarException.class)
  protected ModelAndView handleCarExceptions(CarException ex) {
    Response response = new Response(Status.CAR_ERROR);
    return utils.create(ex, response);
  }

  @ExceptionHandler(CarAlreadyExistsException.class)
  protected ModelAndView handleExistsExceptions(CarAlreadyExistsException ex) {
    Response response = new Response(Status.CAR_EXISTS);
    return utils.create(ex, response);
  }

  @ExceptionHandler(CarNotFoundException.class)
  protected ModelAndView handleNotFoundExceptions(CarNotFoundException ex) {
    Response response = new Response(Status.CAR_NOT_FOUND);
    return utils.create(ex, response);
  }
}
