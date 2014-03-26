package org.egreenbriar.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.form.FormBlock;
import org.egreenbriar.model.Block;
import org.egreenbriar.service.BreadcrumbService;
import org.egreenbriar.service.DatabaseService;
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
    private DatabaseService databaseService = null;
    
    @Autowired
    private BreadcrumbService breadcrumbService = null;
    
    @RequestMapping(value="/block/{blockName}", method=RequestMethod.GET)
    public String communityHandler(Model model, @PathVariable String blockName) throws FileNotFoundException, IOException {
        Block block = databaseService.getBlock(blockName);
        model.addAttribute("block", block);
        
        breadcrumbService.clear();
        breadcrumbService.put("Home", "/");
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
    public String updateCaptain(@ModelAttribute FormBlock formBlock, Model model) throws FileNotFoundException, IOException {
        Block block = databaseService.getBlock(formBlock.getPk());
        if (block == null) {
            throw new RuntimeException("Unable to find block: " + formBlock.getPk());
        }
        block.setCaptainName(formBlock.getValue());
        return block.getCaptainName();
    }
    
    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void setBreadcrumbService(BreadcrumbService breadcrumbService) {
        this.breadcrumbService = breadcrumbService;
    }

}
