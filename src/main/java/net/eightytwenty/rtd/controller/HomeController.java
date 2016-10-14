package net.eightytwenty.rtd.controller;

import net.eightytwenty.rtd.config.RtdApplicationConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static java.util.stream.Collectors.toMap;

@Controller
public class HomeController {

    @RequestMapping("/")
    <E>
    String home(@RequestParam(value = "from", required = false) String from,
                @RequestParam(value = "to", required = false) String to,
                Model model) throws Exception {

        RtdApplicationConfiguration.buildObjectModels();

        return "index";
    }

}
