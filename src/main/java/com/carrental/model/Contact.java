package com.carrental.model;

import javax.persistence.Column;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contacts")
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String email;

  @Column(name = "birthday")
  private LocalDate birthday;

  @Column(name = "note")
  private String note;

  public Contact() {
  }

  public Contact(String name, String phone) {
    this.name = name;
    this.phone = phone;
  }

  public Contact(String name, String phone, String email) {
    this(name, phone);
    this.email = email;
  }

  public Contact(Contact another) {
    this();
    BeanUtils.copyProperties(another, this);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Contact that = (Contact) obj;

    return Objects.equals(this.name, that.name)
        && Objects.equals(this.phone, that.phone)
        && Objects.equals(this.email, that.email)
        && Objects.equals(this.birthday, that.birthday);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, phone, email, birthday);
  }

  @Override
  public String toString() {
    return "Contact{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", phone='" + phone + '\''
        + ", email='" + email + '\''
        + ", birthday=" + birthday
        + ", note='" + note + '\''
        + '}';
  }

  public String getContactInfo() {
    return name.concat(" ").concat(phone);
  }
}
