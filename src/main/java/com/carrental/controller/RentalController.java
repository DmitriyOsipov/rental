package com.carrental.controller;

import com.carrental.domain.Response;
import com.carrental.domain.ResponseKeys;
import com.carrental.exception.RentalException;
import com.carrental.model.Rental;
import com.carrental.service.CarService;
import com.carrental.service.ContactService;
import com.carrental.service.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/rentals")
public class RentalController {

  @Autowired
  private RentalService rentalService;

  @Autowired
  private CarService carService;

  @Autowired
  private ContactService contactService;

  @RequestMapping(method = RequestMethod.GET)
  public String getAll(Model model) {
    model.addAttribute("result", new Response(ResponseKeys.RENTAL_LIST, rentalService.getAll()));
    return "rentals-list";
  }

  @RequestMapping("/current")
  public String get(Model model) {
    model.addAttribute("result",
        new Response(ResponseKeys.RENTAL_LIST, rentalService.getCurrentRentals()));
    return "rentals-list";
  }

  @RequestMapping("/{id}")
  public String get(Model model, @PathVariable long id) throws RentalException {
    Rental rental = rentalService.getRental(id);
    model.addAttribute("result", new Response(ResponseKeys.RENTAL, rental));
    model.addAttribute(rental);
    return "rental-page";
  }

  @PostMapping("/new")
  public String addNew(Model model, @ModelAttribute Rental toAdd) throws RentalException {
    Rental added = rentalService.addRental(toAdd);
    model.addAttribute("result", new Response(ResponseKeys.RENTAL, added));
    return "redirect:/rentals/".concat(String.valueOf(added.getId()));
  }

  @GetMapping("/new")
  public String forNewRental(Model model) {
    Response response = new Response();
    response.put(ResponseKeys.CAR_LIST, carService.getFreeCars());
    response.put(ResponseKeys.CONTACT_LIST, contactService.getAll());
    model.addAttribute("result", response);
    model.addAttribute(new Rental());
    return "rental-new";
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public String update(Model model, @RequestParam("id") long id, @RequestParam("startDate")
      String startDate, @RequestParam("endDate") String endDate,
      @RequestParam("price") double price) throws RentalException {
    Rental updated = rentalService
        .updateRental(id, LocalDate.parse(startDate), LocalDate.parse(endDate), price);
    model.addAttribute("result", new Response(ResponseKeys.RENTAL, updated));
    return "redirect:/rentals/".concat(String.valueOf(updated.getId()));
  }

  @PostMapping("/close")
  public String closeRental(Model model, @RequestParam(name = "id") long id,
      @RequestParam(name = "endMileage") int endMileage) throws RentalException {
    model.addAttribute("result",
        new Response(ResponseKeys.RENTAL, rentalService.closeRental(id, endMileage)));
    return "redirect:/rentals";
  }
}
