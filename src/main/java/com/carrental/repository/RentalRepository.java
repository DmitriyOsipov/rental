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

  List<Rental> findAllByStartDateBeforeAndEndDateAfterOrEndDateIsNullOrEndMileage(LocalDate start,
      LocalDate end, int endMile);

  Rental findFirstByCarAndStartDateBeforeAndEndDateAfterOrEndDateIsNullOrEndMileage(Car car,
      LocalDate start, LocalDate end, int endMile);

  List<Rental> findAllByOrderByStartDateDesc();
}
