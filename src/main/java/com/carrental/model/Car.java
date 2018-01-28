package com.carrental.model;

import org.springframework.beans.BeanUtils;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String type;

  private int mileage;

  public Car() {
  }

  public Car(String type, int mileage) {
    this.type = type;
    this.mileage = mileage;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getMileage() {
    return mileage;
  }

  public void setMileage(int mileage) {
    this.mileage = mileage;
  }

  public Car getCopy() {
    Car copy = new Car();
    BeanUtils.copyProperties(this, copy);
    return copy;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Car that = (Car) obj;

    return Objects.equals(this.type, that.type)
        && Objects.equals(this.mileage, that.mileage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, mileage);
  }

  @Override
  public String toString() {
    return "Car{"
        + "id=" + id
        + ", type='" + type + '\''
        + ", mileage=" + mileage
        + '}';
  }
}
