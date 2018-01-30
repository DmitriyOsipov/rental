package com.carrental.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String login;

  private String password;

  private String role;

  public Account() {
    this.role = "admin";
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Account account = (Account) obj;
    return Objects.equals(login, account.login);
  }

  @Override
  public int hashCode() {

    return Objects.hash(login);
  }

  @Override
  public String toString() {
    return "Account{"
        + "id=" + id
        + ", login='" + login + '\''
        + ", password='" + password + '\''
        + ", role='" + role + '\''
        + '}';
  }
}
