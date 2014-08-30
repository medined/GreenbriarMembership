package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import org.egreenbriar.form.FormDistrict;
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
public class DistrictController {

    @Autowired
    private BreadcrumbService breadcrumbService = null;

    @Autowired
    private BlockService blockService = null;

    @Autowired
    private BlockCaptainService blockCaptainService = null;

    @Autowired
    private HouseService houseService = null;

    @Autowired
    private ChangeService changeService = null;

    @Autowired
    private PeopleService peopleService = null;

    @Autowired
    private OfficierService officierService = null;

    @RequestMapping(value = "/district/{districtName}", method = RequestMethod.GET)
    public String communityHandler(Model model, @PathVariable String districtName) throws FileNotFoundException, IOException {
        model.addAttribute("blockService", blockService);
        model.addAttribute("blockCaptainService", blockCaptainService);
        model.addAttribute("houseService", houseService);
        model.addAttribute("officierService", officierService);
        model.addAttribute("districtName", districtName);
        model.addAttribute("districtRepresentative", officierService.getDistrictRepresentative(districtName));

        Set<String> emails = new TreeSet<>();        
        model.addAttribute("peopleService", getPeopleService());
        for (String personId : getPeopleService().getPeople()) {
            Person person = getPeopleService().getPerson(personId);
            if (person.getDistrictName().equals(districtName) && person.getEmail() != null && !person.getEmail().isEmpty() && person.getEmail().contains("@")) {
                emails.add(person.getEmail());
            }
        }
        model.addAttribute("emails", emails);

        breadcrumbService.clear();
        breadcrumbService.put("Home", "/home");
        breadcrumbService.put("Districts", "/districts");
        breadcrumbService.put(districtName, "");
        breadcrumbService.put("Logout", "/j_spring_security_logout");
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());
        return "district";
    }

    // name=last, value=<new_value>
    @RequestMapping(value = "/district/update_representative", method = RequestMethod.POST)
    @ResponseBody
    public String updateRepresentative(@ModelAttribute FormDistrict formDistrict, Model model) throws FileNotFoundException, IOException {
        final String districtName = formDistrict.getPk();
        String representativeName = formDistrict.getValue();
        representativeName = representativeName.replaceAll(",", "&");
        String message = String.format("district(%s) old(%s) new(%s)", districtName, officierService.getDistrictRepresentative(districtName), representativeName);
        changeService.logChange("update_representative", message);
        officierService.updateDistrictRepresentative(districtName, formDistrict.getValue());
        officierService.write();
        return representativeName;
    }

    public void setChangeService(ChangeService changeService) {
        this.changeService = changeService;
    }

    public void setOfficierService(OfficierService officierService) {
        this.officierService = officierService;
    }

    public BreadcrumbService getBreadcrumbService() {
        return breadcrumbService;
    }

    public void setBlockService(BlockService blockService) {
        this.blockService = blockService;
    }

    public void setBlockCaptainService(BlockCaptainService blockCaptainService) {
        this.blockCaptainService = blockCaptainService;
    }

    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    public void setBreadcrumbService(BreadcrumbService breadcrumbService) {
        this.breadcrumbService = breadcrumbService;
    }

    /**
     * @return the peopleService
     */
    public PeopleService getPeopleService() {
        return peopleService;
    }

    /**
     * @param peopleService the peopleService to set
     */
    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

}
