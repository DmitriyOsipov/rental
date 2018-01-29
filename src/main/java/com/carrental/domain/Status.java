package com.carrental.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {
  OK(200, "OK"),
  ERROR(400, "Some error occured"),
  CAR_ERROR(410,"It's something wrong with that car"),
  CAR_EXISTS(411, "Car with such parameters already exists"),
  CAR_NOT_FOUND(412, "Car wasn't found in DB"),
  CONTACT_ERROR(420, "It's something wrong with that contact"),
  CONTACT_EXISTS(421, "Contact with such parameters already exists"),
  CONTACT_NOT_FOUND(422, "Contact wasn't found in the DB"),
  MAINTENANCE_ERROR(430, "It's something wrong with that maintenance"),
  MAINTENANCE_EXISTS(431, "Maintenance with such parameters already exists"),
  MAINTENANCE_NOT_FOUND(432, "Maintenance wasn't found in the DB"),
  MAINTENANCE_WRONG(433, "Maintenance has incorrect parameters"),
  RENTAL_ERROR(440, "It's something wrong with that rental"),
  RENTAL_EXISTS(441, "Rental with such parameters already exists"),
  RENTAL_NOT_FOUND(442, "Rental wasn't found in the DB"),
  RENTAL_WRONG(443, "Rental has incorrect parameters");

  Status(int code, String message) {
    this.code = code;
    this.message = message;
  }

  private int code;
  private String message;

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "{"
        + "code=" + code
        + ", message='" + message + '\''
        + '}';
  }
}
