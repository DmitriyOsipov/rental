package com.carrental.controller.rest;

import com.carrental.domain.Response;
import com.carrental.domain.ResponseKeys;
import com.carrental.event.CarGetEvent;
import com.carrental.exception.CarException;
import com.carrental.model.Car;
import com.carrental.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/cars")
public class CarRestController {

  @Autowired
  private CarService carService;

  @Autowired
  private ApplicationEventPublisher publisher;

  @RequestMapping(method = RequestMethod.GET)
  public Response getAll() {
    return new Response(ResponseKeys.CAR_LIST, carService.getAllCars());
  }

  @RequestMapping("/{id}")
  public Response getCar(@PathVariable long id) throws CarException {
    Car car = carService.getCar(id);
    Response response = new Response(ResponseKeys.CAR, car);
    publisher.publishEvent(new CarGetEvent(response, car));
    return response;
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public Response addNew(@RequestBody Car car) throws CarException {
    return new Response(ResponseKeys.CAR, carService.addCar(car));
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public Response update(@RequestBody Car car) throws CarException {
    return new Response(ResponseKeys.CAR, carService.updateCar(car));
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public Response delete(@PathVariable long id) throws CarException {
    return new Response(ResponseKeys.CAR_DELETED, carService.deleteCar(id));
  }
}
