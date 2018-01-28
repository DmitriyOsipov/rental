package com.carrental.exception;

public class RentalNotFoundException extends RentalException {

  public RentalNotFoundException() {
    super("There is no such rental in the data base");
  }

  public RentalNotFoundException(String message) {
    super(message);
  }
}
