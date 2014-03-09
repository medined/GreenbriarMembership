package org.egreenbriar.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    /**
     * @param membershipService the membershipService to set
     */
    public void setMembershipService(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

}
