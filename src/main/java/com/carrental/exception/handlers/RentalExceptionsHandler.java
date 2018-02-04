package com.carrental.exception.handlers;

import com.carrental.domain.Response;
import com.carrental.domain.Status;
import com.carrental.exception.RentalAlreadyExistsException;
import com.carrental.exception.RentalException;
import com.carrental.exception.RentalInvalidException;
import com.carrental.exception.RentalNotFoundException;
import com.carrental.exception.handlers.utils.ExceptionHandlerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = {"com.carrental.controller"})
public class RentalExceptionsHandler {

  @Autowired
  private ExceptionHandlerUtil utils;

  @ExceptionHandler(RentalException.class)
  protected ModelAndView handleRentalExceptions(RentalException ex) {
    Response response = new Response(Status.RENTAL_ERROR);
    return utils.create(ex, response);
  }

  @ExceptionHandler(RentalAlreadyExistsException.class)
  protected ModelAndView handleExistsExceptions(RentalAlreadyExistsException ex) {
    Response response = new Response(Status.RENTAL_EXISTS);
    return utils.create(ex, response);
  }

  @ExceptionHandler(RentalNotFoundException.class)
  protected ModelAndView handleNotFoundExceptions(RentalNotFoundException ex) {
    Response response = new Response(Status.RENTAL_NOT_FOUND);
    return utils.create(ex, response);
  }

  @ExceptionHandler(RentalInvalidException.class)
  protected ModelAndView handleWrongExceptions(RentalInvalidException ex) {
    Response response = new Response(Status.RENTAL_WRONG);
    return utils.create(ex, response);
  }
}
