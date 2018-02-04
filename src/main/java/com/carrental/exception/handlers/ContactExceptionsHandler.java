package com.carrental.exception.handlers;

import com.carrental.domain.Response;
import com.carrental.domain.Status;
import com.carrental.exception.ContactAlreadyExistsException;
import com.carrental.exception.ContactException;
import com.carrental.exception.ContactNotFoundException;
import com.carrental.exception.handlers.utils.ExceptionHandlerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = {"com.carrental.controller"})
public class ContactExceptionsHandler {

  @Autowired
  private ExceptionHandlerUtil utils;

  @ExceptionHandler(ContactException.class)
  protected ModelAndView handleContactExceptions(ContactException ex) {
    Response response = new Response(Status.CONTACT_ERROR);
    return utils.create(ex, response);
  }

  @ExceptionHandler(ContactAlreadyExistsException.class)
  protected ModelAndView handleExistsExceptions(ContactAlreadyExistsException ex) {
    Response response = new Response(Status.CONTACT_EXISTS);
    return utils.create(ex, response);
  }

  @ExceptionHandler(ContactNotFoundException.class)
  protected ModelAndView handleExceptions(ContactNotFoundException ex) {
    Response response = new Response(Status.CONTACT_NOT_FOUND);
    return utils.create(ex, response);
  }
}
