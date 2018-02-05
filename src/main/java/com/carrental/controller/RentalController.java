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

  /**
   * method for getting all rentals from DB and show them on rentals-list template
   *
   * @param model - std object
   * @return - Response object with list of rentals for the rental-list template
   */
  @RequestMapping(method = RequestMethod.GET)
  public String getAll(Model model) {
    model.addAttribute("result", new Response(ResponseKeys.RENTAL_LIST, rentalService.getAll()));
    return "rentals-list";
  }

  /**
   * method for getting current rentals from the DB. Current is rental where end mileage isn't set.
   *
   * @param model - std object
   * @return - Response with list of current rentals
   */
  @RequestMapping("/current")
  public String get(Model model) {
    model.addAttribute("result",
        new Response(ResponseKeys.RENTAL_LIST, rentalService.getCurrentRentals()));
    return "rentals-list";
  }

  /**
   * method for getting rental by it's id
   *
   * @param model - std object
   * @param id - rentals id in the DB
   * @return Rental object (in Response) with specified ID for showing it on the rental-page template
   * @throws RentalException can throw RentalNotFoundException if signed ID isn't present in the DB.
   */
  @RequestMapping("/{id}")
  public String get(Model model, @PathVariable long id) throws RentalException {
    Rental rental = rentalService.getRental(id);
    model.addAttribute("result", new Response(ResponseKeys.RENTAL, rental));
    model.addAttribute(rental);
    return "rental-page";
  }

  /**
   * method for creating a new Rental object from data which was got from the web-page form
   *
   * @param model - std object
   * @param toAdd - object from the web-page
   * @return - added object with full data and id
   * @throws RentalException can throw RentalExceptions if there is rental with the same ID
   * or rental parameters are wrong
   */
  @PostMapping("/new")
  public String addNew(Model model, @ModelAttribute Rental toAdd) throws RentalException {
    Rental added = rentalService.addRental(toAdd);
    model.addAttribute("result", new Response(ResponseKeys.RENTAL, added));
    return "redirect:/rentals/".concat(String.valueOf(added.getId()));
  }

  /**
   * prepares rental-new template for further work in creation new rental object
   *
   * @param model - std object
   * @return - list of free cars (cars what aren't in maintenance or another rentals at the moment),
   * the list of registered contacts and empty rental object for setting parameters values for the
   * rental-new template
   */
  @GetMapping("/new")
  public String forNewRental(Model model) {
    Response response = new Response();
    response.put(ResponseKeys.CAR_LIST, carService.getFreeCars());
    response.put(ResponseKeys.CONTACT_LIST, contactService.getAll());
    model.addAttribute("result", response);
    model.addAttribute(new Rental());
    return "rental-new";
  }

  /**
   * method for update an existing rental object. used POST method because of problems with PUT on
   * html web-form
   *
   * @param model - std object
   * @param id - rentals id
   * @param startDate - start date value
   * @param endDate - end date value
   * @param price - rentals price
   * @return - updated rental for rental-page template
   * @throws RentalException - can throw exceptions if rentals id doesn't exist in the DB or rental
   * values are wrong
   */
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public String update(Model model, @RequestParam("id") long id, @RequestParam("startDate")
      String startDate, @RequestParam("endDate") String endDate,
      @RequestParam("price") double price) throws RentalException {
    Rental updated = rentalService
        .updateRental(id, LocalDate.parse(startDate), LocalDate.parse(endDate), price);
    model.addAttribute("result", new Response(ResponseKeys.RENTAL, updated));
    return "redirect:/rentals/".concat(String.valueOf(updated.getId()));
  }

  /**
   * method for closing a rental
   *
   * @param model - std object
   * @param id - rentals id
   * @param endMileage - end mileage of car at the end of rental
   * @return - closed rental and redirects to rentals list page
   * @throws RentalException - can throw exceptions if end mileage value is wrong or rental id
   * doesn't exist in the DB
   */
  @PostMapping("/close")
  public String closeRental(Model model, @RequestParam(name = "id") long id,
      @RequestParam(name = "endMileage") int endMileage) throws RentalException {
    model.addAttribute("result",
        new Response(ResponseKeys.RENTAL, rentalService.closeRental(id, endMileage)));
    return "redirect:/rentals";
  }
}
