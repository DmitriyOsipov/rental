package com.carrental.controller;

import com.carrental.domain.Response;
import com.carrental.domain.ResponseKeys;
import com.carrental.exception.RentalException;
import com.carrental.service.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/rentals")
public class RentalController {

  @Autowired
  private RentalService rentalService;

  @RequestMapping(method = RequestMethod.GET)
  public String getAll(Model model) {
    model.addAttribute("response", new Response(ResponseKeys.RENTAL_LIST, rentalService.getAll()));
    return "rentals";
  }

  @RequestMapping("/current")
  public String get(Model model) {
    model.addAttribute("response",
        new Response(ResponseKeys.RENTAL_LIST, rentalService.getCurrentRentals()));
    return "rentals/current";
  }

}
