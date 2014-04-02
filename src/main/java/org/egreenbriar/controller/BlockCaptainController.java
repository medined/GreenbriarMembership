package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.service.BlockCaptainService;
import org.egreenbriar.service.BlockService;
import org.egreenbriar.service.BreadcrumbService;
import org.egreenbriar.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Component
public class BlockCaptainController {

    @Autowired
    private BreadcrumbService breadcrumbService = null;
    
    @Autowired
    private BlockCaptainService blockCaptainService = null;

    @Autowired
    private BlockService blockService = null;

    @RequestMapping("/blockcaptains")
    public String listHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("blockService", blockService);
        model.addAttribute("captains", blockCaptainService.getCaptains());

        breadcrumbService.clear();
        breadcrumbService.put("Home", "/");
        breadcrumbService.put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());
        
        return "blockcaptains";
    }

    @RequestMapping("/noblockcaptains")
    public String noBlockCaptainHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("blocks", blockCaptainService.getBlocksWithoutCaptains());

        breadcrumbService.getBreadcrumbs().clear();
        breadcrumbService.getBreadcrumbs().put("Home", "/");
        breadcrumbService.getBreadcrumbs().put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());
        
        return "noblockcaptains";
    }

    public void setBreadcrumbService(BreadcrumbService breadcrumbService) {
        this.breadcrumbService = breadcrumbService;
    }

    public void setBlockCaptainService(BlockCaptainService blockCaptainService) {
        this.blockCaptainService = blockCaptainService;
    }

    public void setBlockService(BlockService blockService) {
        this.blockService = blockService;
    }

}
