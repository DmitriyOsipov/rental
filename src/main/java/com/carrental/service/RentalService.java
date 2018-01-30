package com.carrental.service;

import com.carrental.event.RentalClosedEvent;
import com.carrental.exception.RentalAlreadyExistsException;
import com.carrental.exception.RentalException;
import com.carrental.exception.RentalInvalidException;
import com.carrental.exception.RentalNotFoundException;
import com.carrental.model.Car;
import com.carrental.model.Contact;
import com.carrental.model.Rental;
import com.carrental.repository.RentalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

  @Autowired
  private RentalRepository rentalRepository;

  @Autowired
  private MaintenanceService maintenanceService;

  @Autowired
  private ApplicationEventPublisher publisher;

  public Rental addRental(Rental newRental) throws RentalException {
    if (rentalRepository.exists(newRental.getId())) {
      throw new RentalAlreadyExistsException();
    }
    newRental.setEndMileage(0);
    if (maintenanceService.hasUnfinishedMaintenance(newRental.getCar())) {
      throw new RentalInvalidException("This car is under maintenance!");
    }
    return new Rental(rentalRepository.save(newRental));
  }

  public Rental updateRental(Rental updated) throws RentalException {
    if (!rentalRepository.exists(updated.getId())) {
      throw new RentalNotFoundException();
    }
    return new Rental(rentalRepository.save(updated));
  }

  public boolean deleteRental(long id) {
    rentalRepository.delete(id);
    return !rentalRepository.exists(id);
  }

  public Rental getRental(long id) throws RentalException {
    Rental gotOne = rentalRepository.findOne(id);
    if (gotOne == null) {
      throw new RentalNotFoundException();
    }
    return gotOne;
  }

  public List<Rental> getCurrentRentals() {
    return rentalRepository
        .findAllByStartDateBeforeAndEndDateAfter(LocalDate.now(), LocalDate.now());
  }

  public Rental getCurrentRental(Car car) {
    return rentalRepository
        .findFirstByCarAndStartDateBeforeAndEndDateAfter(car, LocalDate.now(), LocalDate.now());
  }

  public List<Rental> getCarRentals(Car car) {
    return rentalRepository.findAllByCar(car);
  }

  public List<Rental> getAll() {
    return rentalRepository.findAll();
  }

  public List<Rental> getClientRentals(Contact client) {
    return rentalRepository.findAllByClient(client);
  }

  public List<Rental> getClientRentals(String contactInfo) {
    return rentalRepository.findAllByContactInfoLike(contactInfo);
  }

  public List<Rental> updateRentalsForClientRemove(Contact client) {
    List<Rental> rentalList = getClientRentals(client);
    rentalList.forEach(rental -> {
      rental.setClient(null);
      rental.setContactInfo(client.getContactInfo());
    });
    return rentalRepository.save(rentalList);
  }

  public Rental closeRental(long id, int endMileage) throws RentalException {
    Rental rental = rentalRepository.findOne(id);
    if (rental == null) {
      throw new RentalNotFoundException();
    }
    if (rental.getStartMileage() <= endMileage) {
      throw new RentalException("Wrong end mileage value!");
    }
    rental.setEndMileage(endMileage);
    Rental closed = new Rental(rentalRepository.save(rental));
    publisher.publishEvent(new RentalClosedEvent(closed.getCar(), endMileage));
    return closed;
  }
}
