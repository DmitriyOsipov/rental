package com.carrental.service;

import com.carrental.exception.CarAlreadyExistsException;
import com.carrental.exception.CarException;
import com.carrental.exception.CarNotFoundException;
import com.carrental.model.Car;
import com.carrental.repository.CarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

  public Car getCar(Long id) throws CarException {
    Car car = repository.findOne(id);
    if (car == null) {
      throw new CarNotFoundException();
    }
    return car;
  }

  public List<Car> getAllCars() {
    return repository.findAll();
  }

  public List<Car> getAllCars(String type) {
    return repository.findAllByTypeLike(type);
  }

  public List<Car> getAllCars(int mileage, int range) {
    return repository.findAllByMileageBetween(mileage - range, mileage + range);
  }
}
