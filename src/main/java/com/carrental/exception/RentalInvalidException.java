package com.carrental.exception;

public class RentalInvalidException extends RentalException {

  public RentalInvalidException() {
    super("Some parameters values are invalid!");
  }

  public RentalInvalidException(String message) {
    super(message);
  }
}
