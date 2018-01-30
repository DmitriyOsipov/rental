package com.carrental.event;

import com.carrental.domain.Response;
import com.carrental.model.Car;

import org.springframework.context.ApplicationEvent;

public class CarGetEvent extends ApplicationEvent {

  private Response response;
  private Car car;

  public CarGetEvent(Response response, Car car) {
    super(car);
    this.response = response;
    this.car = car;
  }

  public Response getResponse() {
    return response;
  }

  public Car getCar() {
    return car;
  }
}
