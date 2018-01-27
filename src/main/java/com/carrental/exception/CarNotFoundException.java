package com.carrental.exception;

public class CarNotFoundException extends CarException {

  public CarNotFoundException() {
    super("There is no such car in the data base.");
  }

  public CarNotFoundException(String message) {
    super(message);
  }
}
