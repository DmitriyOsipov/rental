package com.carrental.exception.handlers;

import com.carrental.domain.Response;
import com.carrental.domain.Status;
import com.carrental.exception.ContactAlreadyExistsException;
import com.carrental.exception.ContactException;
import com.carrental.exception.ContactNotFoundException;
import com.carrental.exception.handlers.utils.ExceptionHandlerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = {"com.carrental.controller"})
public class ContactExceptionsHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private ExceptionHandlerUtil utils;

  @ExceptionHandler(ContactException.class)
  protected ResponseEntity handleContactExceptions(ContactException ex, WebRequest request) {
    Response response = new Response(Status.CONTACT_ERROR);
    return utils.create(ex, request, response);
  }

  @ExceptionHandler(ContactAlreadyExistsException.class)
  protected ResponseEntity handleExistsExceptions(ContactAlreadyExistsException ex,
      WebRequest request) {
    Response response = new Response(Status.CONTACT_EXISTS);
    return utils.create(ex, request, response);
  }

  @ExceptionHandler(ContactNotFoundException.class)
  protected ResponseEntity handleExceptions(ContactNotFoundException ex, WebRequest request) {
    Response response = new Response(Status.CONTACT_NOT_FOUND);
    return utils.create(ex, request, response);
  }
}
