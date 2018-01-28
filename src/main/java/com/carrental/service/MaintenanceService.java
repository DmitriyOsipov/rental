package com.carrental.service;

import com.carrental.exception.MaintenanceAlreadyExistsException;
import com.carrental.exception.MaintenanceException;
import com.carrental.exception.MaintenanceNotFoundException;
import com.carrental.model.Car;
import com.carrental.model.Maintenance;
import com.carrental.model.Maintenance.MaintenanceStatus;
import com.carrental.repository.MaintenanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceService {

  @Autowired
  private MaintenanceRepository repository;

  public Maintenance addMaintenance(Maintenance newMaintenance) throws MaintenanceException {
    if (repository.exists(newMaintenance.getId())) {
      throw new MaintenanceAlreadyExistsException();
    }
    return new Maintenance(repository.save(newMaintenance));
  }

  public Maintenance update(Maintenance updated) throws MaintenanceException {
    if (!repository.exists(updated.getId())) {
      throw new MaintenanceNotFoundException();
    }
    return new Maintenance(repository.save(updated));
  }

  public boolean delete(long id) {
    repository.delete(id);
    return !repository.exists(id);
  }

  public List<Maintenance> getAll() {
    return repository.findAll();
  }

  public List<Maintenance> getAll(Car car) {
    return repository.findAllByCar(car);
  }

  public List<Maintenance> getAll(MaintenanceStatus status) {
    return repository.findAllByStatus(status);
  }

  public List<Maintenance> getAll(Car car, MaintenanceStatus status) {
    return repository.findAllByCarAndStatus(car, status);
  }
}
