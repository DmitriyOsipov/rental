CREATE TABLE accounts
(
  id       BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  login    VARCHAR(45)                 NOT NULL,
  password VARCHAR(45)                 NOT NULL,
  role     VARCHAR(25) DEFAULT 'admin' NOT NULL
)
  ENGINE = InnoDB;

CREATE UNIQUE INDEX login_UNIQUE
  ON accounts (login);

ALTER TABLE accounts
  ADD CONSTRAINT login_UNIQUE
UNIQUE (login);

CREATE TABLE cars
(
  id               BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  car_type         VARCHAR(255)    NULL,
  mileage          INT DEFAULT '0' NULL,
  last_maintenance INT DEFAULT '0' NULL
)
  ENGINE = InnoDB;

CREATE TABLE contacts
(
  id       BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  name     VARCHAR(100) NOT NULL,
  phone    VARCHAR(45)  NOT NULL,
  email    VARCHAR(45)  NULL,
  birthday DATE         NULL,
  note     VARCHAR(255) NULL
)
  ENGINE = InnoDB;

CREATE TABLE maintenances
(
  id        BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  status    INT                NOT NULL,
  date_done DATE               NOT NULL,
  cost      DOUBLE DEFAULT '0' NOT NULL,
  car_id    BIGINT             NOT NULL
)
  ENGINE = InnoDB;

CREATE TABLE rentals
(
  id            BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  car_id        BIGINT       NOT NULL,
  client_id     BIGINT       NULL,
  contact_info  VARCHAR(250) NULL,
  price         DOUBLE       NULL,
  start_date    DATE         NULL,
  end_date      DATE         NULL,
  start_mileage INT          NULL,
  end_mileage   INT          NULL
)
  ENGINE = InnoDB;

CREATE INDEX rentals_cars_id_fk
  ON rentals (car_id);

CREATE INDEX rentals_contacts_id_fk
  ON rentals (client_id);

ALTER TABLE rentals
  ADD CONSTRAINT rentals_cars_id_fk
FOREIGN KEY (car_id) REFERENCES cars (id);

ALTER TABLE rentals
  ADD CONSTRAINT rentals_contacts_id_fk
FOREIGN KEY (client_id) REFERENCES contacts (id) ON DELETE SET NULL;

