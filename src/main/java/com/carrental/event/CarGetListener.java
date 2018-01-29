package com.carrental.event;

import com.carrental.exception.MaintenanceException;
import com.carrental.exception.RentalException;
import com.carrental.service.MaintenanceService;
import com.carrental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

public class CarGetListener {

  @Autowired
  private MaintenanceService maintenanceService;

  @Autowired
  private RentalService rentalService;

  @EventListener
  public void handleCarGetEvent(CarGetEvent event) throws MaintenanceException, RentalException {
    event.getModel().addAttribute("rentals", rentalService.getCarRentals(event.getCar()));
    event.getModel().addAttribute("maintenances", maintenanceService.getAll(event.getCar()));
  }

}
