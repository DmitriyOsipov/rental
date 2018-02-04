package com.carrental.event;

import com.carrental.model.Rental;

import org.springframework.context.ApplicationEvent;

public class RentalCreatedEvent extends ApplicationEvent {

  private Rental rental;

  private long carId;

  public RentalCreatedEvent(Rental rental, long carId) {
    super(rental);
    this.rental = rental;
    this.carId = carId;
  }

  public Rental getRental() {
    return rental;
  }

  public long getCarId() {
    return carId;
  }
}
