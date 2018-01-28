package com.carrental.model;

import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Maintenance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private MaintenanceStatus status;

  private LocalDate dateDone;

  private double cost;

  private Car car;

  public Maintenance() {
  }

  public Maintenance(Car car) {
    this.car = car;
  }

  public Maintenance(Car car, MaintenanceStatus status) {
    this(car);
    this.status = status;
  }

  public Maintenance(Maintenance another) {
    this();
    BeanUtils.copyProperties(another, this);
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

    Maintenance that = (Maintenance) obj;

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
    return "Maintenance{"
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
    IN_PROGRESS,
    DONE;
  }
}
