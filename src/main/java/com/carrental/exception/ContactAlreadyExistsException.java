package com.carrental.exception;

public class ContactAlreadyExistsException extends ContactException {

  public ContactAlreadyExistsException() {
    super("Contact with the same parameters already exists!");
  }

  public ContactAlreadyExistsException(String message) {
    super(message);
  }
}
