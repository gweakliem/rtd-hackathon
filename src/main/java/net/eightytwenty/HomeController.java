package net.eightytwenty;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @RequestMapping("/")
    String home(@RequestParam(value = "from", required=false) String from,
                @RequestParam(value = "to", required=false) String to,
                Model model) {
        model.addAttribute("from", from);
        model.addAttribute("to",to);

        return "index";
    }


}
