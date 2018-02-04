package com.carrental.event;

import com.carrental.exception.CarException;
import com.carrental.exception.RentalException;
import com.carrental.exception.RentalInvalidException;
import com.carrental.model.Rental;
import com.carrental.service.CarService;
import com.carrental.service.ContactService;
import com.carrental.service.MaintenanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RentalCreatedListener {

  @Autowired
  private CarService carService;

  @Autowired
  private ContactService contactService;

  @Autowired
  private MaintenanceService maintenanceService;

  @EventListener
  public void handleRentalCreated(RentalCreatedEvent event) throws CarException, RentalException {
    Rental rental = event.getRental();
    checkDates(rental);
    checkCarForMaintenance(rental);
    setupStartMileage(rental);
    checkContact(rental);
  }

  private void checkContact(Rental rental) throws RentalInvalidException {
    if (contactService.isExisted(rental.getClient().getId())) {
      rental.setContactInfo("");
    } else if (rental.getContactInfo() != null && !rental.getContactInfo().isEmpty()) {
      rental.setClient(null);
    } else {
      throw new RentalInvalidException(
          "Contact should be valid or contact info shouldn't be empty");
    }
  }

  private void setupStartMileage(Rental rental) throws CarException {
    rental.setStartMileage(carService.getCar(rental.getCar().getId()).getMileage());
  }

  private void checkCarForMaintenance(Rental rental) throws RentalInvalidException {
    if (maintenanceService.hasUnfinishedMaintenance(rental.getCar())) {
      throw new RentalInvalidException("This car is under maintenance!");
    }
  }

  private void checkDates(Rental rental) throws RentalInvalidException {
    rental.setStartDate(LocalDate.now());
    if (rental.getEndDate() != null && rental.getEndDate().compareTo(rental.getStartDate()) < 0) {
      throw new RentalInvalidException("End date should be after start date");
    }
  }

}
