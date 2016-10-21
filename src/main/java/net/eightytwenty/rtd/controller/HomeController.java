package net.eightytwenty.rtd.controller;

import net.eightytwenty.rtd.config.RtdApplicationConfiguration;
import net.eightytwenty.rtd.rtdapi.RTDInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import static java.util.stream.Collectors.toMap;

@Controller
public class HomeController {
    private RTDInterface rtdInterface;

    @Autowired
    public HomeController(RTDInterface rtdInterface) {

        this.rtdInterface = rtdInterface;
    }


    @RequestMapping("/")
    String home(@RequestParam(value = "from", required = false) String from,
                @RequestParam(value = "to", required = false) String to,
                Model model) throws Exception {

        RtdApplicationConfiguration.buildObjectModels();

        int rtCount = rtdInterface.getRealtime();

        return "index";
    }

}
