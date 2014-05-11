package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.egreenbriar.service.BlockCaptainRenewalFormService;
import org.egreenbriar.service.BlockCaptainService;
import org.egreenbriar.service.BlockService;
import org.egreenbriar.service.BreadcrumbService;
import org.egreenbriar.service.HouseService;
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
    private HouseService houseService = null;

    @Autowired
    private BlockService blockService = null;
    
    @Autowired
    private BlockCaptainRenewalFormService blockCaptainRenewalFormService = null;

    @RequestMapping("/blockcaptains/pdf")
    public String pdfHandler(Model model) throws FileNotFoundException, IOException {
        try {
            blockCaptainRenewalFormService.process();
        } catch (COSVisitorException e) {
            throw new RuntimeException("Unable to write PDF file", e);
        }
        return "blockcaptainspdf";
    }

    @RequestMapping("/blockcaptains")
    public String blockCaptainHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("blockService", blockService);
        model.addAttribute("captains", blockCaptainService.getCaptains());

        breadcrumbService.clear();
        breadcrumbService.put("Home", "/home");
        breadcrumbService.put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());
        
        return "blockcaptains";
    }

    @RequestMapping("/noblockcaptains")
    public String noBlockCaptainHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("blockService", blockService);
        model.addAttribute("houseService", houseService);
        model.addAttribute("blocks", blockCaptainService.getBlocksWithoutCaptains());

        breadcrumbService.getBreadcrumbs().clear();
        breadcrumbService.getBreadcrumbs().put("Home", "/home");
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

    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    public void setBlockCaptainRenewalFormService(BlockCaptainRenewalFormService blockCaptainRenewalFormService) {
        this.blockCaptainRenewalFormService = blockCaptainRenewalFormService;
    }

}
