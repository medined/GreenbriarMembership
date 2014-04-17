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
    private PeopleService peopleService = null;

    @Autowired
    private ChangeService changeService = null;

    @RequestMapping("/person/toggle_listed/{personId}")
    @ResponseBody
    public String toggleListed(Model model, @PathVariable String personId) throws FileNotFoundException, IOException {
        Person person = peopleService.getPerson(personId);
        String message = String.format("person(%s) current(%s)", personId, person.isListed());
        changeService.logChange("toggle_listed", message);
        person.toggleListed();
        peopleService.write();
        return person.listed();
    }

    @RequestMapping("/person/toggle_directory/{personId}")
    @ResponseBody
    public String toggleDirectory(Model model, @PathVariable String personId) throws FileNotFoundException, IOException {
        Person person = peopleService.getPerson(personId);
        String message = String.format("person(%s) current(%s)", personId, person.isNoDirectory());
        changeService.logChange("toggle_directory", message);
        person.toggleDirectory();
        peopleService.write();
        return person.directory();
    }

    // name=last, value=<new_value>
    @RequestMapping(value = "/person/update_last", method = RequestMethod.POST)
    @ResponseBody
    public String updateLast(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        String personId = formPerson.getPk();
        Person person = peopleService.getPerson(personId);
        String message = String.format("person(%s) old(%s) new(%s)", personId, person.getLast(), formPerson.getValue());
        changeService.logChange("update_last", message);
        person.setLast(formPerson.getValue());
        peopleService.write();
        return person.getLast();
    }

    // name=last, value=<new_value>
    @RequestMapping(value = "/person/update_first", method = RequestMethod.POST)
    @ResponseBody
    public String updateFirst(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        String personId = formPerson.getPk();
        Person person = peopleService.getPerson(personId);
        String message = String.format("person(%s) old(%s) new(%s)", personId, person.getFirst(), formPerson.getValue());
        changeService.logChange("update_first", message);
        person.setFirst(formPerson.getValue());
        peopleService.write();
        return person.getFirst();
    }

    // name=last, value=<new_value>
    @RequestMapping(value = "/person/update_phone", method = RequestMethod.POST)
    @ResponseBody
    public String updatePhone(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        String personId = formPerson.getPk();
        Person person = peopleService.getPerson(personId);
        String message = String.format("person(%s) old(%s) new(%s)", personId, person.getPhone(), formPerson.getValue());
        changeService.logChange("update_phone", message);
        person.setPhone(formPerson.getValue());
        peopleService.write();
        return person.getPhone();
    }

    // name=last, value=<new_value>
    @RequestMapping(value = "/person/update_email", method = RequestMethod.POST)
    @ResponseBody
    public String updateEmail(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        String personId = formPerson.getPk();
        Person person = peopleService.getPerson(personId);
        String message = String.format("person(%s) old(%s) new(%s)", personId, person.getEmail(), formPerson.getValue());
        changeService.logChange("update_email", message);
        person.setEmail(formPerson.getValue());
        peopleService.write();
        return person.getEmail();
    }

    // name=last, value=<new_value>
    @RequestMapping(value = "/person/update_comment", method = RequestMethod.POST)
    @ResponseBody
    public String updateComment(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        String personId = formPerson.getPk();
        Person person = peopleService.getPerson(personId);
        String message = String.format("person(%s) old(%s) new(%s)", personId, person.getComment(), formPerson.getValue());
        changeService.logChange("update_comment", message);
        person.setComment(formPerson.getValue());
        peopleService.write();
        return person.getComment();
    }

    public void setChangeService(ChangeService changeService) {
        this.changeService = changeService;
    }

    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

}
