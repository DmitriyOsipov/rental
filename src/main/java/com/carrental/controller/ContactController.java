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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contacts")
public class ContactController {

  @Autowired
  private ContactService contactService;

  /**
   * method for preparing contacts-search template for search operation
   *
   * @param model - standard object
   * @param searchParam - name of parameter for search operation. can be "name", "phone" or "email"
   * @return prepared contacts-search form for further work
   */
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

  /**
   * method for searching contacts by name
   *
   * @param model - std object
   * @param name - value, which must be contained into searching name (cases invariant)
   * @return a list with contacts with containing @name in their name-field into contacts-list
   * template
   */
  @RequestMapping(value = "/byname")
  public String byName(Model model, @RequestParam("name") String name) {
    model.addAttribute("result",
        new Response(ResponseKeys.CONTACT_LIST, contactService.getAll(name)));
    return "contacts-list";
  }

  /**
   * method for searching contacts by phone
   *
   * @param model - std object
   * @param phone - value, which must be contained into searching phone
   * @return a list with contacts with containing @phone in their phone-field into contacts-list
   * template
   */
  @RequestMapping(value = "/byphone")
  public String byPhone(Model model, @RequestParam("phone") String phone) {
    Contact contact = contactService.getByPhone(phone);
    model.addAttribute("result",
        new Response(ResponseKeys.CONTACT_LIST, contact));
    model.addAttribute("contact", contact);
    return "contact-page";
  }

  /**
   * method for searching contacts by email
   *
   * @param model - std object
   * @param email - value, which must be contained into searching email (cases invariant)
   * @return a list with contacts with containing @email in their email-field into contacts-list
   * template
   */
  @RequestMapping(value = "/byemail")
  public String byEmail(Model model, @RequestParam("email") String email) {
    Contact contact = contactService.getByEmail(email);
    model.addAttribute("result",
        new Response(ResponseKeys.CONTACT_LIST, contact));
    model.addAttribute("contact", contact);
    return "contact-page";
  }

  /**
   * method for getting a full list of contacts from the db
   *
   * @param model - std object
   * @return - a Result object with the list of contacts into contacts-list template
   */
  @RequestMapping(method = RequestMethod.GET)
  public String getAll(Model model) {
    model.addAttribute("result", new Response(ResponseKeys.CONTACT_LIST, contactService.getAll()));
    return "contacts-list";
  }

  /**
   * method for deleting a contact from the DB. Used POST-method instead of DELETE because html-form
   * can't create DELETE-request.
   *
   * @param model - std object
   * @param id - an id of deleting contact
   * @return Response object with the result of deleting operation (true if object with such id
   * wasn't present in the db or was successfully deleted) and redirects into @getAll()
   */
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  public String deleteContact(Model model, @PathVariable("id") long id) {
    model.addAttribute("result",
        new Response(ResponseKeys.CONTACT_DELETED, contactService.delete(id)));
    return "redirect:/contacts";
  }

  /**
   * returns a contact by it's id from the db
   *
   * @param model - std object
   * @param id - an id of contact in the db
   * @return - Response object with a contact, what has signed id
   * @throws ContactException - throws ContactNotFoundException if contact isn't present in the DB.
   */
  @RequestMapping("/{id}")
  public String getContact(Model model, @PathVariable("id") long id) throws ContactException {
    Contact contact = contactService.get(id);
    model.addAttribute("contact", contact);
    model.addAttribute("result", new Response(ResponseKeys.CONTACT, contact));
    return "contact-page";
  }

  /**
   * preparing a contact-new template for creating a new contact
   *
   * @param model - std object
   * @return a new Contact object for contact-new template for further operations
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String prepareAddContact(Model model) {
    model.addAttribute("contact", new Contact());
    return "contact-new";
  }

  /**
   * creating a new contacts from the input data
   *
   * @param model - std object
   * @param contact - a contact object, got from contact-new template
   * @return an added contact and redirects into @getAll()
   * @throws ContactException can throw ContactAlreadyExistsException or a custom ContactException
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String addContact(Model model, @ModelAttribute("contact") Contact contact)
      throws ContactException {
    Contact added = contactService.add(contact);
    model.addAttribute("result", new Response(ResponseKeys.CONTACT, added));
    model.addAttribute("contact", added);
    return "redirect:/contacts";
  }

  /**
   * updating contacts from the input data
   *
   * @param model - std object
   * @param contact - a contact object, got from contact-page template
   * @return an added contact and redirects into @getAll()
   * @throws ContactException can throw ContactNotFoundException or a custom ContactException
   */
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public String updateContact(Model model, @ModelAttribute("contact") Contact contact)
      throws ContactException {
    Contact updated = contactService.update(contact);
    model.addAttribute("result", new Response(ResponseKeys.CONTACT, updated));
    model.addAttribute("contact", updated);
    return "redirect:/contacts";
  }
}
