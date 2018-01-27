package com.carrental.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Maintanance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private MaintenanceStatus status;

  private LocalDate dateDone;

  private double cost;

  private Car car;

  public Maintanance() {
  }

  public Maintanance(Car car) {
    this.car = car;
  }

  public Maintanance(Car car, MaintenanceStatus status) {
    this(car);
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public MaintenanceStatus getStatus() {
    return status;
  }

  public void setStatus(MaintenanceStatus status) {
    this.status = status;
  }

  public LocalDate getDateDone() {
    return dateDone;
  }

  public void setDateDone(LocalDate dateDone) {
    this.dateDone = dateDone;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Maintanance that = (Maintanance) obj;

    return Objects.equals(this.car, that.car)
        && Objects.equals(this.dateDone, that.dateDone)
        && (Math.abs(this.cost - that.cost) <= 0.01);
  }

  @Override
  public int hashCode() {
    return Objects.hash(car, dateDone, cost);
  }

  @Override
  public String toString() {
    return "Maintanance{"
        + "id=" + id
        + ", status=" + status
        + ", dateDone=" + dateDone
        + ", cost=" + cost
        + ", car=" + car
        + '}';
  }

  public enum MaintenanceStatus {
    SCHEDULED,
    ON_REQUEST,
    IN_PROGRESS;
  }
}
