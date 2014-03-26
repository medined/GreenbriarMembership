package org.egreenbriar.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.form.FormPerson;
import org.egreenbriar.model.Person;
import org.egreenbriar.service.DatabaseService;
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
    private DatabaseService databaseService = null;
    
    @RequestMapping("/person/toggle_listed/{personUuid}")
    @ResponseBody
    public String toggleListed(Model model, @PathVariable String personUuid) throws FileNotFoundException, IOException {
        Person person = databaseService.getPerson(personUuid);
        person.toggleListed();
        return person.listed();
    }

    @RequestMapping("/person/toggle_directory/{personUuid}")
    @ResponseBody
    public String toggleDirectory(Model model, @PathVariable String personUuid) throws FileNotFoundException, IOException {
        Person person = databaseService.getPerson(personUuid);
        person.toggleDirectory();
        return person.directory();
    }

    // name=last, value=<new_value>
    @RequestMapping(value="/person/update_last", method = RequestMethod.POST)
    @ResponseBody
    public String updateLast(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        Person person = databaseService.getPerson(formPerson.getPk());
        person.setLast(formPerson.getValue());
        return person.getLast();
    }
    
    // name=last, value=<new_value>
    @RequestMapping(value="/person/update_first", method = RequestMethod.POST)
    @ResponseBody
    public String updateFirst(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        Person person = databaseService.getPerson(formPerson.getPk());
        person.setFirst(formPerson.getValue());
        return person.getFirst();
    }
    
    // name=last, value=<new_value>
    @RequestMapping(value="/person/update_phone", method = RequestMethod.POST)
    @ResponseBody
    public String updatePhone(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        Person person = databaseService.getPerson(formPerson.getPk());
        person.setPhone(formPerson.getValue());
        return person.getPhone();
    }
    
    // name=last, value=<new_value>
    @RequestMapping(value="/person/update_email", method = RequestMethod.POST)
    @ResponseBody
    public String updateEmail(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        Person person = databaseService.getPerson(formPerson.getPk());
        person.setEmail(formPerson.getValue());
        return person.getEmail();
    }
    
    // name=last, value=<new_value>
    @RequestMapping(value="/person/update_comment", method = RequestMethod.POST)
    @ResponseBody
    public String updateComment(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        Person person = databaseService.getPerson(formPerson.getPk());
        person.setComment(formPerson.getValue());
        return person.getComment();
    }
    
    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

}
