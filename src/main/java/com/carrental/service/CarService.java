package com.carrental.service;

import com.carrental.exception.CarAlreadyExistsException;
import com.carrental.exception.CarException;
import com.carrental.exception.CarNotFoundException;
import com.carrental.model.Car;
import com.carrental.repository.CarRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class CarService {

  @Autowired
  private CarRepository repository;

  public Car addCar(Car newCar) throws CarException {
    if (repository.exists(newCar.getId())) {
      throw new CarAlreadyExistsException();
    }
    return repository.save(newCar).getCopy();
  }

  public Car updateCar(Car updated) throws CarException {
    if (!repository.exists(updated.getId())) {
      throw new CarNotFoundException();
    }
    return repository.save(updated).getCopy();
  }

  public boolean deleteCar(Long id) {
    repository.delete(id);
    return repository.exists(id);
  }
}
