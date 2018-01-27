package com.carrental.repository;

import com.carrental.model.Contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

  List<Contact> findAllByNameLike(String name);
  Contact findFirstByPhone(String phone);
  Contact findFirstByEmail(String email);
  List<Contact> findAllByBirthday(LocalDate birthday);

}
