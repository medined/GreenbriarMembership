package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.service.BlockCaptainService;
import org.egreenbriar.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Component
public class BlockCaptainController {

    @Autowired
    private MembershipService membershipService = null;
    
    @RequestMapping("/blockcaptains")
    public String listHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("blocks", membershipService.getBlocks());

        membershipService.getBreadcrumbs().clear();
        membershipService.getBreadcrumbs().put("Home", "/");
        membershipService.getBreadcrumbs().put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", membershipService.getBreadcrumbs());
        
        return "blockcaptains";
    }

    @RequestMapping("/noblockcaptains")
    public String noBlockCaptainHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("blocks", membershipService.getBlocksWithoutCaptains());

        membershipService.getBreadcrumbs().clear();
        membershipService.getBreadcrumbs().put("Home", "/");
        membershipService.getBreadcrumbs().put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", membershipService.getBreadcrumbs());
        
        return "noblockcaptains";
    }

    public void setMembershipService(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

}
