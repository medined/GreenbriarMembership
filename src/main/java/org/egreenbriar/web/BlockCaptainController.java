package org.egreenbriar.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.service.BlockCaptainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Component
public class BlockCaptainController {

    @Autowired
    private BlockCaptainService blockCaptainService = null;
    
    @RequestMapping("/blockcaptains")
    public String listHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("captains", getBlockCaptainService().getCaptains());
        return "blockcaptains";
    }

    /**
     * @return the blockCaptainService
     */
    public BlockCaptainService getBlockCaptainService() {
        return blockCaptainService;
    }

    /**
     * @param blockCaptainService the blockCaptainService to set
     */
    public void setBlockCaptainService(BlockCaptainService blockCaptainService) {
        this.blockCaptainService = blockCaptainService;
    }

}
