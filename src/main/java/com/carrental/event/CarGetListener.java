package com.carrental.event;

import com.carrental.domain.ResponseKeys;
import com.carrental.model.Maintenance;
import com.carrental.service.MaintenanceService;
import com.carrental.service.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarGetListener {

  @Autowired
  private MaintenanceService maintenanceService;

  @Autowired
  private RentalService rentalService;

  @EventListener
  public void handleCarGetEvent(CarGetEvent event) {
    event.getResponse().put(ResponseKeys.RENTAL_LIST, rentalService.getCarRentals(event.getCar()));
    List<Maintenance> maintenances = maintenanceService.getAll(event.getCar());
    event.getResponse()
        .put(ResponseKeys.MAINTENANCE_LIST, maintenances);
    event.getResponse()
        .put(ResponseKeys.MAINTENANCE_TOTAL,
            maintenances.stream().mapToDouble(Maintenance::getCost).sum());
  }

}
