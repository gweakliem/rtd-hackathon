package net.eightytwenty;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @RequestMapping("/")
    String home(Model model) {
        model.addAttribute("name", "foo");

        return "index";
    }

}
