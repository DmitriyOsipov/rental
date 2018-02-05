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

  /**
   * getting a total cost of maintenances in the list
   *
   * @param maintenances - maintenances which total cost we need to know
   * @return Response object with MAINTENANCE_TOTAL field.
   */
  private Response getListResponse(List<Maintenance> maintenances) {
    Response response = new Response(ResponseKeys.MAINTENANCE_LIST, maintenances);
    response.put(ResponseKeys.MAINTENANCE_TOTAL, maintenanceService.getTotalCost(maintenances));
    return response;
  }

  /**
   * method for getting all present in the DB maintenances
   *
   * @param model - std object
   * @return - Response object with list of maintenances in the db and their summary cost for the
   * maintenance-list template
   */
  @RequestMapping
  public String getAll(Model model) {
    List<Maintenance> maintenances = maintenanceService.getAll();
    model.addAttribute("result", getListResponse(maintenances));
    return "maintenances-list";
  }

  /**
   * gets the list of current maintenances and their supposed cost from the DB (currents are
   * maintenances with status IN_PROGRESS or SCHEDULED
   *
   * @return Result object with list of maintenances and their summary cost into maintenances-list
   * template
   */
  @RequestMapping("/current")
  public String current(Model model) {
    List<Maintenance> maintenances = maintenanceService.getAllUnfinished();
    model.addAttribute("result", getListResponse(maintenances));
    return "maintenances-list";
  }

  /**
   * getting an maintenance object from the db by it's id
   *
   * @param model - std object
   * @param id - id of needed maintenance
   * @return - maintenance data into maintenance-page template
   * @throws MaintenanceException - can throw @MaintenanceNotFoundException if id doesn't present in
   * the DB
   */
  @RequestMapping("/{id}")
  public String getOne(Model model, @PathVariable long id) throws MaintenanceException {
    model.addAttribute("result",
        new Response(ResponseKeys.MAINTENANCE, maintenanceService.get(id)));
    return "maintenance-page";
  }

  /**
   * method for closing a maintenance. Puts finally cost and sets maintenance status into DONE
   *
   * @param model - std object
   * @param id - id of maintenance in the db
   * @param total - total cost of the maintenance
   * @return - closed maintenance for the maintenance-page view
   * @throws MaintenanceException - can throw Maintenance exceptions if such id doesn't exist in the
   * DB or maintenance is already closed
   */
  @RequestMapping(value = "/close", method = RequestMethod.POST)
  public String closeMaintenance(Model model, @RequestParam(name = "id") long id,
      @RequestParam(name = "total") double total) throws MaintenanceException {
    model.addAttribute("response",
        new Response(ResponseKeys.MAINTENANCE, maintenanceService.closeMaintenance(id, total)));
    return "redirect:/maintenances/".concat(String.valueOf(id));
  }

  /**
   * sets maintenance status from SCHEDULED into IN_PROGRESS
   *
   * @param model - std object
   * @param id - id of maintenance in the db
   * @return maintenance with changed data
   * @throws MaintenanceException - can throw Maintenance exceptions if such id doesn't exist in the
   * DB or maintenance is already closed
   */
  @RequestMapping(value = "/toprogress", method = RequestMethod.POST)
  public String progressMaintenance(Model model, @RequestParam(name = "id") long id)
      throws MaintenanceException {
    model.addAttribute("result",
        new Response(ResponseKeys.MAINTENANCE,
            maintenanceService.changeStatus(id, MaintenanceStatus.IN_PROGRESS)));
    return "redirect:/maintenances/".concat(String.valueOf(id));
  }

}
