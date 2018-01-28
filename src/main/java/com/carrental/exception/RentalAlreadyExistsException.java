package com.carrental.exception;

public class RentalAlreadyExistsException extends RentalException {

  public RentalAlreadyExistsException() {
    super("A rental with such parameters already exists in the data base");
  }

  public RentalAlreadyExistsException(String message) {
    super(message);
  }
}
