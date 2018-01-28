package com.carrental.service;

import com.carrental.exception.ContactAlreadyExistsException;
import com.carrental.exception.ContactException;
import com.carrental.exception.ContactNotFoundException;
import com.carrental.model.Contact;
import com.carrental.repository.ContactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

@Service
public class ContactService {

  @Autowired
  private ContactRepository repository;

  @Autowired
  private RentalService rentalService;

  public Contact add(Contact newContact) throws ContactException {
    if (repository.exists(newContact.getId())) {
      throw new ContactAlreadyExistsException();
    }
    return new Contact(repository.save(newContact));
  }

  public Contact update(Contact updated) throws ContactException {
    if (!repository.exists(updated.getId())) {
      throw new ContactNotFoundException();
    }
    return new Contact(repository.save(updated));
  }

  @Transactional
  public boolean delete(long id) {
    Contact inDb = repository.findOne(id);
    if (inDb != null) {
      rentalService.updateRentalsForClientRemove(inDb);
      repository.delete(id);
    }
    return !repository.exists(id);
  }

  public List<Contact> getAll() {
    return repository.findAll();
  }

  public List<Contact> getAll(String name) {
    return repository.findAllByNameLike(name);
  }

  public List<Contact> getAll(LocalDate birthday) {
    return repository.findAllByBirthday(birthday);
  }

  public Contact getByPhone(String phone) {
    return repository.findFirstByPhone(phone);
  }

  public Contact getByEmail(String email) {
    return repository.findFirstByEmail(email);
  }
}