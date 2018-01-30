package com.carrental.controller.rest;

import com.carrental.domain.Response;
import com.carrental.domain.ResponseKeys;
import com.carrental.exception.RentalException;
import com.carrental.model.Rental;
import com.carrental.service.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/rentals")
public class RentalRestController {

  @Autowired
  private RentalService rentalService;

  @RequestMapping(method = RequestMethod.GET)
  public Response getAll(Model model) {
    return new Response(ResponseKeys.RENTAL_LIST, rentalService.getAll());
  }

  @RequestMapping("/current")
  public Response get() {
    return new Response(ResponseKeys.RENTAL_LIST, rentalService.getCurrentRentals());
  }

  @RequestMapping("/{id}")
  public Response get(Model model, @PathVariable long id) throws RentalException {
    return new Response(ResponseKeys.RENTAL, rentalService.getRental(id));
  }

  @RequestMapping("/add")
  public Response addNew(Model model, @RequestBody Rental toAdd) throws RentalException {
    return new Response(ResponseKeys.RENTAL, rentalService.addRental(toAdd));
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public Response update(@RequestBody Rental rental) throws RentalException {
    return new Response(ResponseKeys.RENTAL, rentalService.updateRental(rental));
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public Response delete(@PathVariable long id) throws RentalException {
    return new Response(ResponseKeys.RENTAL_DELETED, rentalService.deleteRental(id));
  }

  @RequestMapping(value = "/close", method = RequestMethod.PUT)
  public Response closeRental(@RequestParam(name = "id") long id,
      @RequestParam(name = "endMile") int endMile) throws RentalException {
    return new Response(ResponseKeys.RENTAL, rentalService.closeRental(id, endMile));
  }
}
