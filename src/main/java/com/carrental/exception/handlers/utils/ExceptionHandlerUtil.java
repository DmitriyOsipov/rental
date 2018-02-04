package com.carrental.exception.handlers.utils;

import com.carrental.domain.Response;
import com.carrental.domain.ResponseKeys;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ExceptionHandlerUtil {

  public ModelAndView create(Exception exception, Response response) {
    response.put(ResponseKeys.EXCEPTION_TYPE, exception.getClass());
    response.put(ResponseKeys.EXCEPTION_MESSAGE, exception.getMessage());

    ModelAndView mav = new ModelAndView();
    mav.addObject("result", response);
    mav.setViewName("error-part");
    return mav;
  }
}
