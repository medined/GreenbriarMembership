package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.model.House;
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
public class HouseController {

    @Autowired
    private MembershipService membershipService = null;
    
    @RequestMapping("/house/toggle_2014_membership/{houseUuid}")
    @ResponseBody
    public void toggle2014Membership(Model model, @PathVariable String houseUuid) throws FileNotFoundException, IOException {
        House house = membershipService.getHouse(houseUuid);
        house.toggle2014Membership();
    }

    /**
     * @param membershipService the membershipService to set
     */
    public void setMembershipService(MembershipService membershipService) {
        this.membershipService = membershipService;
    }
}
