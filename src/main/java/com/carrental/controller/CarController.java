package com.carrental.controller;

import com.carrental.event.CarGetEvent;
import com.carrental.exception.CarException;
import com.carrental.model.Car;
import com.carrental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cars")
public class CarController {

  @Autowired
  private CarService carService;

  @Autowired
  private ApplicationEventPublisher publisher;

  @RequestMapping(method = RequestMethod.GET)
  public String getAll(Model model) {
    model.addAttribute("carsList", carService.getAllCars());
    return "cars";
  }

  @RequestMapping("/{id}")
  public String getCar(Model model, @PathVariable long id) throws CarException {
    Car car = carService.getCar(id);
    model.addAttribute("car", car);
    publisher.publishEvent(new CarGetEvent(model, car));
    return "cars/".concat(String.valueOf(id));
  }
}
