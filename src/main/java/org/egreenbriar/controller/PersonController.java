package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.form.FormPerson;
import org.egreenbriar.model.Person;
import org.egreenbriar.service.BreadcrumbService;
import org.egreenbriar.service.ChangeService;
import org.egreenbriar.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
public class PersonController {

    @Autowired
    private BreadcrumbService breadcrumbService = null;
        
    @Autowired
    private PeopleService peopleService = null;

    @Autowired
    private ChangeService changeService = null;

    @RequestMapping("/person/toggle_listed/{personUuid}")
    @ResponseBody
    public String toggleListed(Model model, @PathVariable String personUuid) throws FileNotFoundException, IOException {
        Person person = peopleService.getPerson(personUuid);
        //String message = String.format("houseNumber(%s) streetName(%s) old(%b) new(%b)", house.getHouseNumber(), house.getStreetName(), house.isMember2014(), !house.isMember2014());
        //changeService.logChange("toggle_listed", message);
        person.toggleListed();
        peopleService.write();
        return person.listed();
    }

    @RequestMapping("/person/toggle_directory/{personUuid}")
    @ResponseBody
    public String toggleDirectory(Model model, @PathVariable String personUuid) throws FileNotFoundException, IOException {
        Person person = peopleService.getPerson(personUuid);
        person.toggleDirectory();
        return person.directory();
    }

    // name=last, value=<new_value>
    @RequestMapping(value="/person/update_last", method = RequestMethod.POST)
    @ResponseBody
    public String updateLast(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        Person person = peopleService.getPerson(formPerson.getPk());
        person.setLast(formPerson.getValue());
        return person.getLast();
    }
    
    // name=last, value=<new_value>
    @RequestMapping(value="/person/update_first", method = RequestMethod.POST)
    @ResponseBody
    public String updateFirst(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        Person person = peopleService.getPerson(formPerson.getPk());
        person.setFirst(formPerson.getValue());
        return person.getFirst();
    }
    
    // name=last, value=<new_value>
    @RequestMapping(value="/person/update_phone", method = RequestMethod.POST)
    @ResponseBody
    public String updatePhone(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        Person person = peopleService.getPerson(formPerson.getPk());
        person.setPhone(formPerson.getValue());
        return person.getPhone();
    }
    
    // name=last, value=<new_value>
    @RequestMapping(value="/person/update_email", method = RequestMethod.POST)
    @ResponseBody
    public String updateEmail(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        Person person = peopleService.getPerson(formPerson.getPk());
        person.setEmail(formPerson.getValue());
        return person.getEmail();
    }
    
    // name=last, value=<new_value>
    @RequestMapping(value="/person/update_comment", method = RequestMethod.POST)
    @ResponseBody
    public String updateComment(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        Person person = peopleService.getPerson(formPerson.getPk());
        person.setComment(formPerson.getValue());
        return person.getComment();
    }
    
    public void setChangeService(ChangeService changeService) {
        this.changeService = changeService;
    }

    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    public BreadcrumbService getBreadcrumbService() {
        return breadcrumbService;
    }

}
