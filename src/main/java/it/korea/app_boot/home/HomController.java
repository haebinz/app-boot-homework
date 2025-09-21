package it.korea.app_boot.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomController {

    @GetMapping("/home")
    public String getHomeView() {
        return "views/example";
    }
    
}
