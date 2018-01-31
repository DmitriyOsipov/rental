package com.carrental.controller.rest;

import com.carrental.domain.Response;
import com.carrental.domain.ResponseKeys;
import com.carrental.exception.MaintenanceException;
import com.carrental.model.Maintenance;
import com.carrental.service.MaintenanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/maintenances")
public class MaintenanceRestController {

  @Autowired
  private MaintenanceService maintenanceService;

  @RequestMapping
  public Response current() {
    return new Response(ResponseKeys.MAINTENANCE_LIST, maintenanceService.getAllUnfinished());
  }

  @RequestMapping("/{id}")
  public Response getOne(@PathVariable long id) throws MaintenanceException {
    return new Response(ResponseKeys.MAINTENANCE, maintenanceService.get(id));
  }

  @RequestMapping("/add")
  public Response addNew(@RequestBody Maintenance toAdd) throws MaintenanceException {
    return new Response(ResponseKeys.MAINTENANCE, maintenanceService.addMaintenance(toAdd));
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public Response update(@RequestBody Maintenance toUpdate) throws MaintenanceException {
    return new Response(ResponseKeys.MAINTENANCE, maintenanceService.update(toUpdate));
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public Response delete(@PathVariable long id) throws MaintenanceException {
    return new Response(ResponseKeys.MAINTENANCE_DELETED, maintenanceService.delete(id));
  }

  @RequestMapping(value = "/close", method = RequestMethod.PUT)
  public Response closeMaintenance(@RequestParam(name = "id") long id,
      @RequestParam(name = "total") double total) throws MaintenanceException {
    return new Response(ResponseKeys.MAINTENANCE, maintenanceService.closeMaintenance(id, total));
  }
}
