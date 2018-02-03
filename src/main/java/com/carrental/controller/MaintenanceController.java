package com.carrental.controller;

import com.carrental.domain.Response;
import com.carrental.domain.ResponseKeys;
import com.carrental.exception.MaintenanceException;
import com.carrental.model.Maintenance;
import com.carrental.model.Maintenance.MaintenanceStatus;
import com.carrental.service.MaintenanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/maintenances")
public class MaintenanceController {

  @Autowired
  private MaintenanceService maintenanceService;

  private Response getListResponse(List<Maintenance> maintenances) {
    Response response = new Response(ResponseKeys.MAINTENANCE_LIST, maintenances);
    response.put(ResponseKeys.MAINTENANCE_TOTAL, maintenanceService.getTotalCost(maintenances));
    return response;
  }

  @RequestMapping
  public String getAll(Model model) {
    List<Maintenance> maintenances = maintenanceService.getAll();
    model.addAttribute("result", getListResponse(maintenances));
    return "maintenances-list";
  }

  @RequestMapping("/current")
  public String current(Model model) {
    List<Maintenance> maintenances = maintenanceService.getAllUnfinished();
    model.addAttribute("result", getListResponse(maintenances));
    return "maintenances-list";
  }

  @RequestMapping("/{id}")
  public String getOne(Model model, @PathVariable long id) throws MaintenanceException {
    model.addAttribute("result",
        new Response(ResponseKeys.MAINTENANCE, maintenanceService.get(id)));
    return "maintenance-page";
  }

  @RequestMapping(value = "/close", method = RequestMethod.POST)
  public String closeMaintenance(Model model, @RequestParam(name = "id") long id,
      @RequestParam(name = "total") double total) throws MaintenanceException {
    model.addAttribute("response",
        new Response(ResponseKeys.MAINTENANCE, maintenanceService.closeMaintenance(id, total)));
    return "redirect:/maintenances/".concat(String.valueOf(id));
  }

  @RequestMapping(value = "/toprogress", method = RequestMethod.POST)
  public String progressMaintenance(Model model, @RequestParam(name = "id") long id)
      throws MaintenanceException {
    model.addAttribute("result",
        new Response(ResponseKeys.MAINTENANCE,
            maintenanceService.changeStatus(id, MaintenanceStatus.IN_PROGRESS)));
    return "redirect:/maintenances/".concat(String.valueOf(id));
  }

}
