package com.carrental.controller;

import com.carrental.domain.Response;
import com.carrental.domain.ResponseKeys;
import com.carrental.exception.RentalException;
import com.carrental.model.Rental;
import com.carrental.service.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rentals")
public class RentalController {

  @Autowired
  private RentalService rentalService;

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
    model.addAttribute("result", new Response(ResponseKeys.RENTAL, rentalService.getRental(id)));
    return "rental-page";
  }

  @RequestMapping("/add")
  public String addNew(Model model, @RequestBody Rental toAdd) throws RentalException {
    Rental added = rentalService.addRental(toAdd);
    model.addAttribute("result", new Response(ResponseKeys.RENTAL, added));
    return "rentals/".concat(String.valueOf(added.getId()));
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public String update(Model model, @RequestBody Rental rental) throws RentalException {
    Rental updated = rentalService.updateRental(rental);
    model.addAttribute("result", new Response(ResponseKeys.RENTAL, updated));
    return "redirect:/rentals/".concat(String.valueOf(updated.getId()));
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public String delete(Model model, @PathVariable long id) throws RentalException {
    model.addAttribute("result",
        new Response(ResponseKeys.RENTAL_DELETED, rentalService.deleteRental(id)));
    return "redirect:/rentals";
  }

  @RequestMapping(value = "/close", method = RequestMethod.PUT)
  public String closeRental(Model model, @RequestParam(name = "id") long id,
      @RequestParam(name = "endMile") int endMile) throws RentalException {
    model.addAttribute("result",
        new Response(ResponseKeys.RENTAL, rentalService.closeRental(id, endMile)));
    return "rentals-list";
  }
}
