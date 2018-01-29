package com.carrental.event;

import com.carrental.model.Car;
import org.springframework.context.ApplicationEvent;
import org.springframework.ui.Model;

public class CarGetEvent extends ApplicationEvent {

  private Model model;
  private Car car;

  public CarGetEvent(Model model, Car car) {
    super(car);
    this.model = model;
    this.car = car;
  }

  public Model getModel() {
    return model;
  }

  public Car getCar() {
    return car;
  }
}
