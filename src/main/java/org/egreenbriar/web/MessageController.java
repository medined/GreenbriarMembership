package org.egreenbriar.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {

    @RequestMapping("/message/list")
    @ResponseBody
    public String listHandler() {
        return "Message List";
    }
}
