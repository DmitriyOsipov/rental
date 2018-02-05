package com.carrental.controller;

import com.carrental.domain.Response;
import com.carrental.domain.ResponseKeys;
import com.carrental.event.CarGetEvent;
import com.carrental.exception.CarException;
import com.carrental.model.Car;
import com.carrental.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cars")
public class CarController {

  @Autowired
  private CarService carService;

  @Autowired
  private ApplicationEventPublisher publisher;

  /**
   * Method for getting full list of cars in the DB
   *
   * @param model - standard org.springframework.ui.Model object
   * @return com.carrental.domain.Response object with list of cars in DB to the cars-list template
   */
  @GetMapping
  public String getAll(Model model) {
    Response response = new Response();
    response.put(ResponseKeys.CAR_LIST, carService.getAllCars());
    model.addAttribute("result", response);
    return "cars-list";
  }

  /**
   * Getting a car with it's rentals and maintenances from db by it's id.
   *
   * @param model - standard org.springframework.ui.Model object
   * @param id - car's id in the DB
   * @return - Response object with car, list of rentals and list of maintenances into car-page
   * template
   * @throws CarException if car with signed id isn't present in the DB
   */
  @GetMapping("/{id}")
  public String getCar(Model model, @PathVariable long id) throws CarException {
    model.addAttribute(new Car());
    Car car = carService.getCar(id);
    Response response = new Response(ResponseKeys.CAR, car);
    publisher.publishEvent(new CarGetEvent(response, car));
    model.addAttribute("result", response);
    model.addAttribute(car);
    return "car-page";
  }

  /**
   * Prepares car-new template for creation a new car
   *
   * @param model - std object
   * @return - empty car object into car-new template for further work
   */
  @GetMapping("/new")
  public String addNew(Model model) {
    model.addAttribute(new Car());
    return "car-new";
  }

  /**
   * this method adds a new car into the db
   *
   * @param car - car object which was taken from car-new template
   * @param model standard object
   * @return added car with signed id and redirects to @getAll() method
   * @throws CarException can throw CarAlreadyExistException if car with the same id already present
   * in the db
   */
  @PostMapping("/new")
  public String addNew(@ModelAttribute Car car, Model model)
      throws CarException {
    Car added = carService.addCar(car);
    model.addAttribute("result", new Response(ResponseKeys.CAR, added));
    model.addAttribute("car", added);
    return "redirect:/cars/".concat(String.valueOf(car.getId()));
  }

  /**
   * method for updating an existed car.doesn't attached to any view
   *
   * @param model - std object
   * @param car - modified car, which was got from car-page template
   * @return updated car into car-page template
   * @throws CarException can throw CarNotFoundException if car with signed id doesn't exist in the
   * db
   */
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public String update(Model model, @ModelAttribute Car car) throws CarException {
    Car updated = carService.updateCar(car);
    model.addAttribute("result", new Response(ResponseKeys.CAR, updated));
    model.addAttribute("car", updated);
    return "car-page";
  }

  /**
   * method for deleting a car from db. doesn't attached to any view
   *
   * @param model - std object
   * @param id - id of the car in the db
   * @return Response object with boolean value in field CAR_DELETED. It is true if car wasn't in
   * the db or was successfully deleted from it
   */
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public String delete(Model model, @PathVariable long id) {
    model.addAttribute("result",
        new Response(ResponseKeys.CAR_DELETED, carService.deleteCar(id)));
    return "redirect:/cars";
  }
}
