package com.carrental.service;

import com.carrental.event.RentalClosedEvent;
import com.carrental.event.RentalCreatedEvent;
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
  private ApplicationEventPublisher publisher;

  public Rental addRental(Rental newRental) throws RentalException {
    if (rentalRepository.exists(newRental.getId())) {
      throw new RentalAlreadyExistsException();
    }
    publisher.publishEvent(new RentalCreatedEvent(newRental, newRental.getCar().getId()));
    return new Rental(rentalRepository.save(newRental));
  }

  public Rental updateRental(Rental updated) throws RentalException {
    if (!rentalRepository.exists(updated.getId())) {
      throw new RentalNotFoundException();
    }
    return new Rental(rentalRepository.save(updated));
  }

  public Rental updateRental(long id, LocalDate startDate, LocalDate endDate, double price)
      throws RentalException {
    if (startDate.compareTo(endDate) > 0) {
      throw new RentalInvalidException("End date should be after start date");
    }
    if (price < 0) {
      throw new RentalInvalidException("Price should be positive");
    }
    Rental inDb = rentalRepository.getOne(id);
    if (inDb == null) {
      throw new RentalNotFoundException();
    }
    if (inDb.getEndMileage() > 0) {
      throw new RentalInvalidException("Rental is already closed!");
    }
    inDb.setStartDate(startDate);
    inDb.setEndDate(endDate);
    inDb.setPrice(price);
    return rentalRepository.save(inDb);
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
        .findAllByStartDateBeforeAndEndDateAfterOrEndDateIsNullOrEndMileage(LocalDate.now(),
            LocalDate.now(), 0);
  }

  public Rental getCurrentRental(Car car) {
    return rentalRepository
        .findFirstByCarAndStartDateBeforeAndEndDateAfterOrEndDateIsNullOrEndMileage(car,
            LocalDate.now(),
            LocalDate.now(), 0);
  }

  public List<Rental> getCarRentals(Car car) {
    return rentalRepository.findAllByCarOrderByStartDateDesc(car);
  }

  public List<Rental> getAll() {
    return rentalRepository.findAllByOrderByStartDateDesc();
  }

  public List<Rental> getClientRentals(Contact client) {
    return rentalRepository.findAllByClientOrderByStartDateDesc(client);
  }

  public List<Rental> getClientRentals(String contactInfo) {
    return rentalRepository.findAllByContactInfoLikeOrderByStartDateDesc(contactInfo);
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
    if (rental.getStartMileage() >= endMileage) {
      throw new RentalException("Wrong end mileage value!");
    }
    rental.setEndMileage(endMileage);
    Rental closed = new Rental(rentalRepository.save(rental));
    publisher.publishEvent(new RentalClosedEvent(closed.getCar(), endMileage));
    return closed;
  }
}
