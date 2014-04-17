package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;
import org.egreenbriar.form.FormNewPerson;
import org.egreenbriar.model.Block;
import org.egreenbriar.model.House;
import org.egreenbriar.model.Person;
import org.egreenbriar.service.BlockCaptainService;
import org.egreenbriar.service.BlockService;
import org.egreenbriar.service.BreadcrumbService;
import org.egreenbriar.service.ChangeService;
import org.egreenbriar.service.HouseService;
import org.egreenbriar.service.OfficierService;
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
public class HouseController {

    @Autowired
    private BreadcrumbService breadcrumbService = null;

    @Autowired
    private BlockService blockService = null;

    @Autowired
    private HouseService houseService = null;

    @Autowired
    private BlockCaptainService blockCaptainService = null;

    @Autowired
    private OfficierService officierService = null;

    @Autowired
    private PeopleService peopleService = null;

    @Autowired
    private ChangeService changeService = null;

    @RequestMapping(value = "/house/add_person/{houseUuid}", method = RequestMethod.GET)
    public String addPersonForm(Model model, @PathVariable String houseUuid) throws FileNotFoundException, IOException {
        House house = houseService.getHouse(houseUuid);
        Block block = blockService.getBlock(house.getBlockName());

        model.addAttribute("house", house);
        model.addAttribute("blockName", house.getBlockName());
        model.addAttribute("districtName", house.getDistrictName());

        breadcrumbService.clear();
        breadcrumbService.put("Home", "/");
        breadcrumbService.put("Districts", "/districts");
        breadcrumbService.put(block.getDistrictName(), "/district/" + block.getDistrictName());
        breadcrumbService.put(house.getBlockName(), "");
        breadcrumbService.put("Add Person", "");
        breadcrumbService.put("Logout", "/j_spring_security_logout");
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());

        return "addperson";
    }

    // name=last, value=<new_value>
    @RequestMapping(value="/house/newperson", method = RequestMethod.POST)
    public String newPerson(@ModelAttribute FormNewPerson form, Model model) throws FileNotFoundException, IOException {

        String blockName = form.getBlockName();
        House house = houseService.getHouse(form.getHouseId());
        
        Person person = new Person();
        person.setPk(UUID.randomUUID().toString());
        person.setDistrictName(blockName);
        person.setBlockName(blockName);
        person.setHouseNumber(house.getHouseNumber());
        person.setStreetName(house.getStreetName());
        person.setLast(form.getLastName());
        person.setFirst(form.getFirstName());
        person.setPhone(form.getPhone());
        person.setEmail(form.getEmail());
        person.setComment(form.getComments());
        person.setUnlisted(form.getUnlisted().equals("1"));
        person.setNoDirectory(form.getNodirectory().equals("1"));
        
        String personFormat = "house(%s) first(%s) last(%s) phone(%s) email(%s) comments(%s)";
        String message = String.format(personFormat, form.getHouseId(), person.getFirst(), person.getLast(), person.getPhone(), person.getEmail(), person.getComment());
        changeService.logChange("newperson", message);
        
        peopleService.addPerson(person);
        peopleService.write();

        //model.addAttribute("blockService", blockService);
        //model.addAttribute("blockCaptainService", blockCaptainService);
        //model.addAttribute("houseService", houseService);
        //model.addAttribute("officierService", officierService);
        //model.addAttribute("peopleService", peopleService);
        
        //final String districtName = blockService.getDistrictName(blockName);

        model.addAttribute("blockName", blockName);
        //model.addAttribute("districtName", districtName);
        //model.addAttribute("blockCaptain", blockCaptainService.getCaptainName(blockName));
        //model.addAttribute("districtRepresentative", officierService.getDistrictRepresentative(districtName));

        //Block block = blockService.getBlock(blockName);
        //model.addAttribute("block", block);
        
        //breadcrumbService.clear();
        //breadcrumbService.put("Home", "/");
        //breadcrumbService.put("Districts", "/districts");
        //breadcrumbService.put(block.getDistrictName(), "/district/" + block.getDistrictName());
        //breadcrumbService.put(blockName, "");
        //breadcrumbService.put("Logout", "/j_spring_security_logout");        
        //model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());
        
        return "redirect:/block";
    }

    @RequestMapping("/house/toggle_2014_membership/{houseUuid}")
    @ResponseBody
    public void toggle2014Membership(Model model, @PathVariable String houseUuid) throws FileNotFoundException, IOException {
        House house = houseService.getHouse(houseUuid);
        String message = String.format("houseNumber(%s) streetName(%s) current(%b)", house.getHouseNumber(), house.getStreetName(), house.isMember2014());
        changeService.logChange("toggle_2014_membership", message);
        house.toggle2014Membership();
        houseService.write();
    }

    public void setBlockService(BlockService blockService) {
        this.blockService = blockService;
    }

    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    public void setBreadcrumbService(BreadcrumbService breadcrumbService) {
        this.breadcrumbService = breadcrumbService;
    }

    public void setChangeService(ChangeService changeService) {
        this.changeService = changeService;
    }

    public void setBlockCaptainService(BlockCaptainService blockCaptainService) {
        this.blockCaptainService = blockCaptainService;
    }

    public void setOfficierService(OfficierService officierService) {
        this.officierService = officierService;
    }

    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

}
