package com.carrental.event;

import com.carrental.domain.ResponseKeys;
import com.carrental.service.MaintenanceService;
import com.carrental.service.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CarGetListener {

  @Autowired
  private MaintenanceService maintenanceService;

  @Autowired
  private RentalService rentalService;

  @EventListener
  public void handleCarGetEvent(CarGetEvent event) {
    event.getResponse().put(ResponseKeys.RENTAL_LIST, rentalService.getCarRentals(event.getCar()));
    event.getResponse()
        .put(ResponseKeys.MAINTENANCE_LIST, maintenanceService.getAll(event.getCar()));
  }

}
