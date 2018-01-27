package com.carrental.repository;

import com.carrental.model.Car;
import com.carrental.model.Contact;
import com.carrental.model.Rental;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

  List<Rental> findAllByCar(Car car);
  List<Rental> findAllByContactInfoLike(String contactInfo);
  List<Rental> findAllByClient(Contact client);
  List<Rental> findAllByStartDateBeforeAndEndDateAfter(LocalDate start, LocalDate end);
  Rental findFirstByCarAndStartDateBeforeAndEndDateAfter(Car car, LocalDate start, LocalDate end);
}
