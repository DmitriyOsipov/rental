package com.carrental.exception;

public class MaintenanceNotFoundException extends MaintenanceException {

  public MaintenanceNotFoundException() {
    super("There is no such maintenance in the data base.");
  }

  public MaintenanceNotFoundException(String message) {
    super(message);
  }
}
