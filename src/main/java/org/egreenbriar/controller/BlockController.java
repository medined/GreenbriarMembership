package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.form.FormBlock;
import org.egreenbriar.model.Block;
import org.egreenbriar.service.BlockCaptainService;
import org.egreenbriar.service.ChangeService;
import org.egreenbriar.service.MembershipService;
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
    private MembershipService membershipService = null;

    @Autowired
    private BlockCaptainService blockCaptainService = null;

    @Autowired
    private ChangeService changeService = null;

    @RequestMapping(value="/block/{blockName}", method=RequestMethod.GET)
    public String communityHandler(Model model, @PathVariable String blockName) throws FileNotFoundException, IOException {
        Block block = membershipService.getBlock(blockName);
        model.addAttribute("block", block);
        
        membershipService.getBreadcrumbs().clear();
        membershipService.getBreadcrumbs().put("Home", "/");
        membershipService.getBreadcrumbs().put("Districts", "/districts");
        membershipService.getBreadcrumbs().put(block.getDistrictName(), "/district/" + block.getDistrictName());
        membershipService.getBreadcrumbs().put(blockName, "");
        membershipService.getBreadcrumbs().put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", membershipService.getBreadcrumbs());

        return "block";
    }

    // name=last, value=<new_value>
    @RequestMapping(value="/block/update_captain", method = RequestMethod.POST)
    @ResponseBody
    public String updateCaptain(@ModelAttribute FormBlock formBlock, Model model) throws FileNotFoundException, IOException {
        Block block = membershipService.getBlock(formBlock.getPk());
        if (block == null) {
            throw new RuntimeException("Unable to find block: " + formBlock.getPk());
        }
        String message = String.format("block(%s) old(%s) new(%s)", block.getBlockName(), block.getCaptainName(), formBlock.getValue());
        changeService.logChange("update_captain", message);
        block.setCaptainName(formBlock.getValue());
        blockCaptainService.write(membershipService.getBlocks());
        return block.getCaptainName();
    }
    
    public void setMembershipService(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    public void setBlockCaptainService(BlockCaptainService blockCaptainService) {
        this.blockCaptainService = blockCaptainService;
    }

    public void setChangeService(ChangeService changeService) {
        this.changeService = changeService;
    }

}
