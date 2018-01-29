package com.carrental.event;

import com.carrental.model.Car;
import org.springframework.context.ApplicationEvent;

public class RentalClosedEvent extends ApplicationEvent {

  private Car car;

  private int endMileage;

  public RentalClosedEvent(Car car, int endMileage) {
    super(car);
    this.car = car;
    this.endMileage = endMileage;
  }

  public Car getCar() {
    return car;
  }

  public int getEndMileage() {
    return endMileage;
  }
}
