package com.carrental.controller;

import com.carrental.domain.Response;
import com.carrental.domain.ResponseKeys;
import com.carrental.exception.MaintenanceException;
import com.carrental.model.Maintenance;
import com.carrental.service.MaintenanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/maintenances")
public class MaintenanceController {

  @Autowired
  private MaintenanceService maintenanceService;

  @RequestMapping
  public String current(Model model) {
    model.addAttribute("response",
        new Response(ResponseKeys.MAINTENANCE_LIST, maintenanceService.getAllUnfinished()));
    return "maintenance";
  }

  @RequestMapping("/{id}")
  public String getOne(Model model, @PathVariable long id) throws MaintenanceException {
    model.addAttribute("response",
        new Response(ResponseKeys.MAINTENANCE, maintenanceService.get(id)));
    return "maintenance/".concat(String.valueOf(id));
  }

  @RequestMapping("/add")
  public String addNew(Model model, @RequestBody Maintenance toAdd) throws MaintenanceException {
    Maintenance added = maintenanceService.addMaintenance(toAdd);
    model.addAttribute("response", new Response(ResponseKeys.MAINTENANCE, added));
    return "maintenance/".concat(String.valueOf(added.getId()));
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public String update(Model model, @RequestBody Maintenance toUpdate) throws MaintenanceException {
    Maintenance updated = maintenanceService.update(toUpdate);
    model.addAttribute("response", new Response(ResponseKeys.MAINTENANCE, updated));
    return "maintenance/".concat(String.valueOf(updated.getId()));
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public String delete(Model model, @PathVariable long id) throws MaintenanceException {
    model.addAttribute("response",
        new Response(ResponseKeys.MAINTENANCE_DELETED, maintenanceService.delete(id)));
    return "maintenance";
  }

  @RequestMapping(value = "/close", method = RequestMethod.PUT)
  public String closeMaintenance(Model model, @RequestParam(name = "id") long id,
      @RequestParam(name = "total") double total) throws MaintenanceException {
    model.addAttribute("response",
        new Response(ResponseKeys.MAINTENANCE, maintenanceService.closeMaintenance(id, total)));
    return "maintenance/".concat(String.valueOf(id));
  }

}
