package com.carrental.repository;

import com.carrental.model.Car;
import com.carrental.model.Contact;
import com.carrental.model.Rental;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

  List<Rental> findAllByCarOrderByStartDateDesc(Car car);

  List<Rental> findAllByContactInfoLikeOrderByStartDateDesc(String contactInfo);

  List<Rental> findAllByClientOrderByStartDateDesc(Contact client);

  List<Rental> findAllByEndMileage(int endMile);

  Rental findFirstByCarAndEndMileage(Car car, int endMile);

  List<Rental> findAllByOrderByStartDateDesc();
}
