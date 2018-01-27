package com.carrental.repository;

import com.carrental.model.Car;
import com.carrental.model.Maintanance;
import com.carrental.model.Maintanance.MaintenanceStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintanance, Long> {

  List<Maintanance> findAllByCar(Car car);

  List<Maintanance> findAllByCarAndStatus(Car car, MaintenanceStatus status);

  List<Maintanance> findAllByStatus(MaintenanceStatus status);

}
