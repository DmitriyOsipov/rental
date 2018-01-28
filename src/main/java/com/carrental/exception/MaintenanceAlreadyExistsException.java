package com.carrental.exception;

public class MaintenanceAlreadyExistsException extends MaintenanceException {

  public MaintenanceAlreadyExistsException() {
    super("A maintenance with such parameters already exists in the data base");
  }

  public MaintenanceAlreadyExistsException(String message) {
    super(message);
  }
}
