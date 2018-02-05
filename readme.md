This is a small (and study) application with Spring Boot, Hibernate, H2DB and Thymeleaf for visual displaying.
And it is first project where I used Thymeleaf, so it was interesting.

This is some Car rental service, where you can do next things:
- add new cars, contacts (clients) or rentals;
- delete every contact at the moment, even if there is unfinished rental with him. In this case field _contactInfo_ in rental object will be filled;
- watch all clients, cars, rentals or maintenances;
- watch information about concrete car, client, rental or maintenance; On the car page you also can see all it rentals and maintenances;
- watch current rentals (with end mileage "0") or maintenances (with status _SCHEDULED_ or _IN_PROGRESS_);
- close rentals;
- change maintenance status or finish it;

For enter just run project and go to the **http://localhost:8080/** in your browser.

There is no authorization (there wasn't a task to do it).