package com.carrental.controller;

import com.carrental.domain.Response;
import com.carrental.domain.ResponseKeys;
import com.carrental.domain.Status;
import com.carrental.exception.ContactException;
import com.carrental.exception.RentalException;
import com.carrental.model.Contact;
import com.carrental.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contacts")
public class ContactController {

  @Autowired
  private ContactService contactService;

  @RequestMapping(value = "/searchby", method = RequestMethod.GET)
  public String searchBy(Model model, @RequestParam("param") String searchParam) {
    switch (searchParam) {
      case "name": {
        model.addAttribute("searchParam", "name");
      }
      break;
      case "phone": {
        model.addAttribute("searchParam", "phone");
      }
      break;
      case "email": {
        model.addAttribute("searchParam", "email");
      }
      break;
      default: {
        Response response = new Response(Status.ERROR);
        response.put(ResponseKeys.EXCEPTION_MESSAGE, "Wrong search parameter type");
        response.put(ResponseKeys.EXCEPTION_TYPE, RentalException.class);
        model.addAttribute("result", response);
        return "error-part";
      }
    }
    return "contacts-search";
  }

  @RequestMapping(value = "/byname")
  public String byName(Model model, @RequestParam("name") String name) {
    model.addAttribute("result",
        new Response(ResponseKeys.CONTACT_LIST, contactService.getAll(name)));
    return "contacts-list";
  }

  @RequestMapping(value = "/byphone")
  public String byPhone(Model model, @RequestParam("phone") String phone) {
    Contact contact = contactService.getByPhone(phone);
    model.addAttribute("result",
        new Response(ResponseKeys.CONTACT_LIST, contact));
    model.addAttribute("contact", contact);
    return "contact-page";
  }

  @RequestMapping(value = "/byemail")
  public String byEmail(Model model, @RequestParam("email") String email) {
    Contact contact = contactService.getByEmail(email);
    model.addAttribute("result",
        new Response(ResponseKeys.CONTACT_LIST, contact));
    model.addAttribute("contact", contact);
    return "contact-page";
  }

  @RequestMapping(method = RequestMethod.GET)
  public String getAll(Model model) {
    model.addAttribute("result", new Response(ResponseKeys.CONTACT_LIST, contactService.getAll()));
    return "contacts-list";
  }

  @PostMapping("/delete/{id}")
  public String deleteContact(Model model, @PathVariable("id") long id) {
    model.addAttribute("result",
        new Response(ResponseKeys.CONTACT_DELETED, contactService.delete(id)));
    return "redirect:/contacts";
  }

  @RequestMapping("/{id}")
  public String getContact(Model model, @PathVariable("id") long id) throws ContactException {
    Contact contact = contactService.get(id);
    model.addAttribute("contact", contact);
    model.addAttribute("result", new Response(ResponseKeys.CONTACT, contact));
    return "contact-page";
  }
}
