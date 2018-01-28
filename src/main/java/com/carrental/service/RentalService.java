package com.carrental.service;

import com.carrental.exception.RentalAlreadyExistsException;
import com.carrental.exception.RentalException;
import com.carrental.model.Rental;
import com.carrental.repository.RentalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

  @Autowired
  private RentalRepository rentalRepository;

  public Rental addRental(Rental newRental) throws RentalException {
    if (rentalRepository.exists(newRental.getId())) {
      throw new RentalAlreadyExistsException();
    }
    return rentalRepository.save(newRental);
  }

}
