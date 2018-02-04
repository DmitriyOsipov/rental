package com.carrental.exception.handlers;

import com.carrental.domain.Response;
import com.carrental.domain.Status;
import com.carrental.exception.handlers.utils.ExceptionHandlerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = {"com.carrental.controller"})
public class RuntimeExceptionsHandler {

  @Autowired
  private ExceptionHandlerUtil utils;

  @ExceptionHandler(RuntimeException.class)
  protected ModelAndView handleExceptions(RuntimeException ex) {
    Response response = new Response(Status.ERROR);
    return utils.create(ex, response);
  }

}
