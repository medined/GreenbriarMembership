package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.form.FormBlock;
import org.egreenbriar.model.Block;
import org.egreenbriar.service.BlockCaptainService;
import org.egreenbriar.service.BlockService;
import org.egreenbriar.service.BreadcrumbService;
import org.egreenbriar.service.ChangeService;
import org.egreenbriar.service.DistrictService;
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
public class BlockController {

    @Autowired
    private BreadcrumbService breadcrumbService = null;
    
    @Autowired
    private BlockService blockService = null;
    
    @Autowired
    private BlockCaptainService blockCaptainService = null;

    @Autowired
    private DistrictService districtService = null;

    @Autowired
    private HouseService houseService = null;

    @Autowired
    private OfficierService officierService = null;

    @Autowired
    private PeopleService peopleService = null;

    @Autowired
    private ChangeService changeService = null;

    @RequestMapping(value="/editeveryhouse", method=RequestMethod.GET)
    public String editeveryhouse(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("blockService", blockService);
        model.addAttribute("blockCaptainService", blockCaptainService);
        model.addAttribute("districtService", districtService);
        model.addAttribute("houseService", houseService);
        model.addAttribute("officierService", officierService);
        model.addAttribute("peopleService", peopleService);
                
        breadcrumbService.clear();
        breadcrumbService.put("Home", "/home");
        breadcrumbService.put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());

        return "editeveryhouse";
    }
    
    @RequestMapping(value="/block/{blockName}", method=RequestMethod.GET)
    public String displayBlock01(Model model, @PathVariable String blockName) throws FileNotFoundException, IOException {
        model.addAttribute("blockService", blockService);
        model.addAttribute("blockCaptainService", blockCaptainService);
        model.addAttribute("houseService", houseService);
        model.addAttribute("officierService", officierService);
        model.addAttribute("peopleService", peopleService);
        
        final String districtName = blockService.getDistrictName(blockName);

        model.addAttribute("blockName", blockName);
        model.addAttribute("districtName", districtName);
        model.addAttribute("blockCaptain", blockCaptainService.getCaptainName(blockName));
        model.addAttribute("districtRepresentative", officierService.getDistrictRepresentative(districtName));

        Block block = blockService.getBlock(blockName);
        model.addAttribute("block", block);
        
        breadcrumbService.clear();
        breadcrumbService.put("Home", "/home");
        breadcrumbService.put("Districts", "/districts");
        breadcrumbService.put(block.getDistrictName(), "/district/" + block.getDistrictName());
        breadcrumbService.put(blockName, "");
        breadcrumbService.put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());

        return "block";
    }

    @RequestMapping(value="/block", method=RequestMethod.GET)
    public String displayBlock02(@ModelAttribute FormBlock form, Model model) throws FileNotFoundException, IOException {
        final String blockName = form.getBlockName();
        model.addAttribute("blockService", blockService);
        model.addAttribute("blockCaptainService", blockCaptainService);
        model.addAttribute("houseService", houseService);
        model.addAttribute("officierService", officierService);
        model.addAttribute("peopleService", peopleService);
        
        final String districtName = blockService.getDistrictName(blockName);

        model.addAttribute("blockName", blockName);
        model.addAttribute("districtName", districtName);
        model.addAttribute("blockCaptain", blockCaptainService.getCaptainName(blockName));
        model.addAttribute("districtRepresentative", officierService.getDistrictRepresentative(districtName));

        Block block = blockService.getBlock(blockName);
        model.addAttribute("block", block);
        
        breadcrumbService.clear();
        breadcrumbService.put("Home", "/home");
        breadcrumbService.put("Districts", "/districts");
        breadcrumbService.put(block.getDistrictName(), "/district/" + block.getDistrictName());
        breadcrumbService.put(blockName, "");
        breadcrumbService.put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());

        return "block";
    }

    // name=last, value=<new_value>
    @RequestMapping(value="/block/update_captain", method = RequestMethod.POST)
    @ResponseBody
    public void updateCaptain(@ModelAttribute FormBlock formBlock, Model model) throws FileNotFoundException, IOException {
        String captainName = blockCaptainService.getCaptainName(formBlock.getPk());
        captainName = captainName.replaceAll(",", "&");
        String message = String.format("block(%s) old(%s) new(%s)", formBlock.getPk(), captainName, formBlock.getValue());
        changeService.logChange("update_captain", message);
        blockCaptainService.update(formBlock.getPk(), formBlock.getValue());
    }
    
    public void setBlockCaptainService(BlockCaptainService blockCaptainService) {
        this.blockCaptainService = blockCaptainService;
    }

    public void setChangeService(ChangeService changeService) {
        this.changeService = changeService;
    }

    public void setBlockService(BlockService blockService) {
        this.blockService = blockService;
    }

    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    public void setOfficierService(OfficierService officierService) {
        this.officierService = officierService;
    }

    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    public void setBreadcrumbService(BreadcrumbService breadcrumbService) {
        this.breadcrumbService = breadcrumbService;
    }

    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

}
