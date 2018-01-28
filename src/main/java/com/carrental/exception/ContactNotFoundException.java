package com.carrental.exception;

public class ContactNotFoundException extends ContactException {

  public ContactNotFoundException() {
    super("There is no such contact in the data base.");
  }

  public ContactNotFoundException(String message) {
    super(message);
  }
}
