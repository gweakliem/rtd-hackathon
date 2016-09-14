package net.eightytwenty;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@Controller
public class HomeController {

    @RequestMapping("/")
    String home(@RequestParam(value = "from", required=false) String from,
                @RequestParam(value = "to", required=false) String to,
                Model model) throws Exception {
        model.addAttribute("from", from);
        model.addAttribute("to",to);

        List<Long> decode = DataPuller.decode(DataPuller.download());
//
//        List<Long> decode= asList(1234L, 3456L, 3466L);

        model.addAttribute("routeInfo", new StopInfo("10", "14496", decode));

        return "index";
    }


}
