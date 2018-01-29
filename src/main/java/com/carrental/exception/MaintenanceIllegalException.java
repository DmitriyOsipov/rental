package com.carrental.exception;

public class MaintenanceIllegalException extends MaintenanceException {

  public MaintenanceIllegalException() {
    super("Some parameters values are illegal!");
  }

  public MaintenanceIllegalException(String message) {
    super(message);
  }
}
