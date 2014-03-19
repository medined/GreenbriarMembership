package org.egreenbriar.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Component
public class DistrictsController {

    @Autowired
    private MembershipService membershipService = null;

    @RequestMapping("/districts")
    public String communityHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("community", membershipService.getCommunity());
        model.addAttribute("districts", membershipService.getDistricts());
        model.addAttribute("houses", membershipService.getHouses());
        
        membershipService.getBreadcrumbs().clear();
        membershipService.getBreadcrumbs().put("Home", "/");
        membershipService.getBreadcrumbs().put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", membershipService.getBreadcrumbs());

        return "districts";
    }

    /**
     * @param membershipService the membershipService to set
     */
    public void setMembershipService(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

}
