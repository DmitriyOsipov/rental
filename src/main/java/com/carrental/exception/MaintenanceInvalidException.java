package com.carrental.exception;

public class MaintenanceInvalidException extends MaintenanceException {

  public MaintenanceInvalidException() {
    super("Some parameters values are invalid!");
  }

  public MaintenanceInvalidException(String message) {
    super(message);
  }
}
