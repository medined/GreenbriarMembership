package org.egreenbriar.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.form.FormBlock;
import org.egreenbriar.model.Block;
import org.egreenbriar.model.Person;
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

    @RequestMapping(value="/block/{blockName}", method=RequestMethod.GET)
    public String communityHandler(Model model, @PathVariable String blockName) throws FileNotFoundException, IOException {
        model.addAttribute("block", membershipService.getBlock(blockName));
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
        block.setCaptainName(formBlock.getValue());
        return block.getCaptainName();
    }
    
    /**
     * @param membershipService the membershipService to set
     */
    public void setMembershipService(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

}
