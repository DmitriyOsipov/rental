package com.carrental.service;

import com.carrental.exception.CarAlreadyExistsException;
import com.carrental.exception.CarException;
import com.carrental.exception.CarNotFoundException;
import com.carrental.model.Car;
import com.carrental.model.Maintenance;
import com.carrental.model.Rental;
import com.carrental.repository.CarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

  @Autowired
  private CarRepository repository;

  @Autowired
  private MaintenanceService maintenanceService;

  @Autowired
  private RentalService rentalService;

  public Car addCar(Car newCar) throws CarException {
    if (repository.exists(newCar.getId())) {
      throw new CarAlreadyExistsException();
    }
    return new Car(repository.save(newCar));
  }

  public Car updateCar(Car updated) throws CarException {
    if (!repository.exists(updated.getId())) {
      throw new CarNotFoundException();
    }
    return new Car(repository.save(updated));
  }

  public boolean deleteCar(Long id) {
    repository.delete(id);
    return !repository.exists(id);
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

  public List<Car> getFreeCars() {
    List<Car> allCars = this.getAllCars();
    allCars.removeAll(maintenanceService.getAllUnfinished()
        .stream().map(Maintenance::getCar).collect(Collectors.toList()));
    allCars.removeAll(rentalService.getCurrentRentals()
        .stream().map(Rental::getCar).collect(Collectors.toList()));
    return allCars;
  }
}
