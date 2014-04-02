package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.form.FormDistrict;
import org.egreenbriar.model.District;
import org.egreenbriar.service.ChangeService;
import org.egreenbriar.service.MembershipService;
import org.egreenbriar.service.OfficierService;
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
    private MembershipService membershipService = null;

    @Autowired
    private ChangeService changeService = null;

    @Autowired
    private OfficierService officierService = null;

    @RequestMapping(value="/district/{name}", method=RequestMethod.GET)
    public String communityHandler(Model model, @PathVariable String name) throws FileNotFoundException, IOException {
        model.addAttribute("district", membershipService.getDistrict(name));
        membershipService.getBreadcrumbs().clear();
        membershipService.getBreadcrumbs().put("Home", "/");
        membershipService.getBreadcrumbs().put("Districts", "/districts");
        membershipService.getBreadcrumbs().put(name, "");
        membershipService.getBreadcrumbs().put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", membershipService.getBreadcrumbs());

        return "district";
    }

    // name=last, value=<new_value>
    @RequestMapping(value="/district/update_representative", method = RequestMethod.POST)
    @ResponseBody
    public String updateRepresentative(@ModelAttribute FormDistrict formDistrict, Model model) throws FileNotFoundException, IOException {
        District district = membershipService.getDistrict(formDistrict.getPk());
        String message = String.format("district(%s) old(%s) new(%s)", district.getName(), district.getRepresentative(), formDistrict.getValue());
        changeService.logChange("update_representative", message);
        officierService.updateDistrictRepresentative(district.getName(), formDistrict.getValue());
        district.setRepresentative(formDistrict.getValue());
        officierService.write();
        return district.getRepresentative();
    }
    
    public void setMembershipService(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    public void setChangeService(ChangeService changeService) {
        this.changeService = changeService;
    }

    public void setOfficierService(OfficierService officierService) {
        this.officierService = officierService;
    }

}
