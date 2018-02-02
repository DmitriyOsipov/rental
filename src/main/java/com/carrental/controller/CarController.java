package com.carrental.controller;

import com.carrental.domain.Response;
import com.carrental.domain.ResponseKeys;
import com.carrental.event.CarGetEvent;
import com.carrental.exception.CarAlreadyExistsException;
import com.carrental.exception.CarException;
import com.carrental.model.Car;
import com.carrental.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/cars")
public class CarController {

  @Autowired
  private CarService carService;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public String getAll(Model model) throws CarException {
    Response response = new Response();
    response.put(ResponseKeys.CAR_LIST, carService.getAllCars());
    model.addAttribute("result", response);
    //throw new CarAlreadyExistsException();
    return "cars-list";
  }

  @GetMapping("/{id}")
  public String getCar(Model model, @PathVariable long id) throws CarException {
    model.addAttribute(new Car());
    Car car = carService.getCar(id);
    Response response = new Response(ResponseKeys.CAR, car);
    publisher.publishEvent(new CarGetEvent(response, car));
    model.addAttribute("result", response);
    return "car-page";
  }

  @GetMapping("/new")
  public String addNew(Model model) {
    model.addAttribute(new Car());
    return "car-new";
  }

  @PostMapping("/new")
  public String addNew(@ModelAttribute Car car, Model model, SessionStatus sessionStatus)
      throws CarException {
    Car added = carService.addCar(car);
    sessionStatus.setComplete();
    model.addAttribute("result", new Response(ResponseKeys.CAR, added));
    return "redirect:/cars/".concat(String.valueOf(car.getId()));
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public String update(Model model, @RequestBody Car toUpdate) throws CarException {
    Car updated = carService.updateCar(toUpdate);
    model.addAttribute("result", new Response(ResponseKeys.CAR, updated));
    return "car-page";
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public String delete(Model model, @PathVariable long id) throws CarException {
    model.addAttribute("result",
        new Response(ResponseKeys.CAR_DELETED, carService.deleteCar(id)));
    return "redirect:/cars";
  }
}
