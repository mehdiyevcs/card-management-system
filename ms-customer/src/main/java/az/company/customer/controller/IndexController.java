package az.company.customer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MehdiyevCS on 24.08.21
 */
@RestController
@RequestMapping("/api")
public class IndexController {

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok(String.format("Hey, I am %s", applicationName));
    }
}
