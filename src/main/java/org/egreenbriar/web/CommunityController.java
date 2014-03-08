package org.egreenbriar.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
public class CommunityController {

    @Value("${membership.csv.file}")
    String membershipFile = "2013_Greenbriar_Membership.csv";

    @Value("${streets.csv.file}")
    String streetFile = "greenbriar_streets.csv";

    @Value("${blockcaptain.csv.file}")
    String captainFile = "2013_greenbriar_block_captains.csv";

    @RequestMapping("/community")
    @ResponseBody
    public String communityHandler() {
        return membershipFile;
    }
}
