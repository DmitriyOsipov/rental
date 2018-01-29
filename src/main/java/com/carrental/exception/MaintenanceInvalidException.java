package com.carrental.exception;

public class MaintenanceInvalidException extends MaintenanceException {

  public MaintenanceInvalidException() {
    super("Some parameters values are illegal!");
  }

  public MaintenanceInvalidException(String message) {
    super(message);
  }
}
