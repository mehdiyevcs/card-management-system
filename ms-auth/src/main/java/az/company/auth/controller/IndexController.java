package az.company.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MehdiyevCS on 20.08.21
 */
@RestController
@RequestMapping("/api")
public class IndexController {
    @GetMapping("/ping")
    public String ping(){
        return "Hello, i am ms-auth";
    }
}
