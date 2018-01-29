package com.carrental.repository;

import com.carrental.model.Car;
import com.carrental.model.Maintenance;
import com.carrental.model.Maintenance.MaintenanceStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

  List<Maintenance> findAllByCar(Car car);

  List<Maintenance> findAllByCarAndStatus(Car car, MaintenanceStatus status);

  List<Maintenance> findAllByStatus(MaintenanceStatus status);

  List<Maintenance> findAllByStatusIsNot(MaintenanceStatus status);
}
