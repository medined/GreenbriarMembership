package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
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

    @RequestMapping("/person/emails")
    public String emails(Model model) {
        Set<String> emails = new TreeSet<>();
        
        model.addAttribute("peopleService", peopleService);

        Map<String, Person> people = peopleService.getPeople();
        for (Entry<String, Person> entry : people.entrySet()) {
            Person person = entry.getValue();
            if (person.getEmail() != null && !person.getEmail().isEmpty() && person.getEmail().contains("@")) {
                emails.add(person.getEmail());
            }
        }
        model.addAttribute("emails", emails);
        
        breadcrumbService.clear();
        breadcrumbService.put("Home", "/home");
        breadcrumbService.put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());

        return "emails";
    }
    
    @RequestMapping("/person/bad_emails")
    public String bad_emails(Model model) {
        model.addAttribute("peopleService", peopleService);

        breadcrumbService.clear();
        breadcrumbService.put("Home", "/home");
        breadcrumbService.put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());

        return "badEmails";
    }
    
    @RequestMapping("/person/delete/{personId}")
    public String deletePerson(Model model, @PathVariable String personId) throws FileNotFoundException, IOException {
        Person person = peopleService.getPerson(personId);
        
        StringBuilder buffer = new StringBuilder();
        buffer.append(String.format("person(%s)", personId));
        buffer.append(String.format(" district(%s)", person.getDistrictName()));
        buffer.append(String.format(" block(%s)", person.getBlockName()));
        buffer.append(String.format(" houseNumber(%s)", person.getHouseNumber()));
        buffer.append(String.format(" streetName(%s)", person.getStreetName()));
        buffer.append(String.format(" first(%s)", person.getFirst()));
        buffer.append(String.format(" last(%s)", person.getLast()));
        buffer.append(String.format(" phone(%s)", person.getPhone()));
        buffer.append(String.format(" email(%s)", person.getEmail()));
        buffer.append(String.format(" comment(%s)", person.getComment()));
        buffer.append(String.format(" unlisted(%s)", person.isUnlisted()));
        buffer.append(String.format(" comment(%s)", person.getComment()));

        changeService.logChange("deletePerson", buffer.toString());
        peopleService.deletePerson(person);
        peopleService.write();
        model.addAttribute("blockName", person.getBlockName());
        
        return "redirect:/block";
    }

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
        String lastName = formPerson.getValue().replaceAll(",", "&");
        String message = String.format("person(%s) old(%s) new(%s)", personId, person.getLast(), lastName);
        changeService.logChange("update_last", message);
        person.setLast(lastName);
        peopleService.write();
        return person.getLast();
    }

    // name=last, value=<new_value>
    @RequestMapping(value = "/person/update_first", method = RequestMethod.POST)
    @ResponseBody
    public String updateFirst(@ModelAttribute FormPerson formPerson, Model model) throws FileNotFoundException, IOException {
        String personId = formPerson.getPk();
        Person person = peopleService.getPerson(personId);
        String firstName = formPerson.getValue().replaceAll(",", "&");
        String message = String.format("person(%s) old(%s) new(%s)", personId, person.getFirst(), firstName);
        changeService.logChange("update_first", message);
        person.setFirst(firstName);
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
        String comment = formPerson.getValue().replaceAll(",", ";");
        String message = String.format("person(%s) old(%s) new(%s)", personId, person.getComment(), comment);
        changeService.logChange("update_comment", message);
        person.setComment(comment);
        peopleService.write();
        return person.getComment();
    }

    public void setChangeService(ChangeService changeService) {
        this.changeService = changeService;
    }

    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    public void setBreadcrumbService(BreadcrumbService breadcrumbService) {
        this.breadcrumbService = breadcrumbService;
    }

}
