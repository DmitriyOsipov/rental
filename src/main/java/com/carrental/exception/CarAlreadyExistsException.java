package com.carrental.exception;

public class CarAlreadyExistsException extends CarException {

  public CarAlreadyExistsException() {
    super("Car with the same parameters already exists!");
  }

  public CarAlreadyExistsException(String message) {
    super(message);
  }
}
