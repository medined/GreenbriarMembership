package org.egreenbriar.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.model.Person;
import org.egreenbriar.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
public class PersonController {

    @Autowired
    private MembershipService membershipService = null;
    
    @RequestMapping("/person/toggle_listed/{personUuid}")
    @ResponseBody
    public String toggleListed(Model model, @PathVariable String personUuid) throws FileNotFoundException, IOException {
        Person person = membershipService.getPerson(personUuid);
        person.toggleListed();
        return person.listed();
    }

    @RequestMapping("/person/toggle_directory/{personUuid}")
    @ResponseBody
    public String toggleDirectory(Model model, @PathVariable String personUuid) throws FileNotFoundException, IOException {
        Person person = membershipService.getPerson(personUuid);
        person.toggleDirectory();
        return person.directory();
    }

    /**
     * @param membershipService the membershipService to set
     */
    public void setMembershipService(MembershipService membershipService) {
        this.membershipService = membershipService;
    }
}
