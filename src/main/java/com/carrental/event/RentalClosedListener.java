package com.carrental.event;

import com.carrental.exception.CarException;
import com.carrental.exception.MaintenanceException;
import com.carrental.model.Car;
import com.carrental.model.Maintenance;
import com.carrental.model.Maintenance.MaintenanceStatus;
import com.carrental.service.CarService;
import com.carrental.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RentalClosedListener {

  @Autowired
  private CarService carService;

  @Autowired
  private MaintenanceService maintenanceService;

  @Async
  @EventListener
  public void handleRentalClosed(RentalClosedEvent event)
      throws MaintenanceException, CarException {
    Car car = event.getCar();
    car.setMileage(event.getEndMileage());
    if (car.getMileage() - car.getLastMaintenance() >= 50_000) {
      maintenanceService.addMaintenance(new Maintenance(car, MaintenanceStatus.SCHEDULED));
    }
    car.setLastMaintenance(car.getLastMaintenance() + 50_000);
    carService.updateCar(car);
  }
}
