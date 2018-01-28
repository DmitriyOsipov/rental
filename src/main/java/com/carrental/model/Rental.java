package com.carrental.model;

import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rentals")
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "car_id")
  private Car car;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Contact client;

  private String contactInfo;

  private double price;

  private LocalDate startDate;

  private LocalDate endDate;

  private int startMileage;

  private int endMileage;

  public Rental() {
  }

  private Rental(Car car, double price, LocalDate startDate, LocalDate endDate, int startMileage) {
    this.car = car;
    this.price = price;
    this.startDate = startDate;
    this.endDate = endDate;
    this.startMileage = startMileage;
  }

  public Rental(Car car, Contact client, double price, LocalDate startDate,
      LocalDate endDate, int startMileage) {
    this(car, price, startDate, endDate, startMileage);
    this.client = client;
  }

  public Rental(Car car, String contactInfo, double price, LocalDate startDate,
      LocalDate endDate, int startMileage) {
    this(car, price, startDate, endDate, startMileage);
    this.contactInfo = contactInfo;
  }

  public Rental(Rental another) {
    this();
    BeanUtils.copyProperties(another, this);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public Contact getClient() {
    return client;
  }

  public void setClient(Contact client) {
    this.client = client;
  }

  public String getContactInfo() {
    return contactInfo;
  }

  public void setContactInfo(String contactInfo) {
    this.contactInfo = contactInfo;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public int getStartMileage() {
    return startMileage;
  }

  public void setStartMileage(int startMileage) {
    this.startMileage = startMileage;
  }

  public int getEndMileage() {
    return endMileage;
  }

  public void setEndMileage(int endMileage) {
    this.endMileage = endMileage;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Rental that = (Rental) obj;

    return Objects.equals(this.car, that.car)
        && Objects.equals(this.client, that.client)
        && Objects.equals(this.contactInfo, that.contactInfo)
        && Objects.equals(this.startDate, that.startDate)
        && Objects.equals(this.endDate, that.endDate)
        && Objects.equals(this.startMileage, that.startMileage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(car, client, contactInfo, startDate, endDate, startMileage);
  }

  @Override
  public String toString() {
    return "Rental{"
        + "id=" + id
        + ", car=" + car
        + ", client=" + client
        + ", contactInfo='" + contactInfo + '\''
        + ", price=" + price
        + ", startDate=" + startDate
        + ", endDate=" + endDate
        + '}';
  }
}
