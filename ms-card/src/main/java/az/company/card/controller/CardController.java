package az.company.card.controller;

import az.company.card.dto.CardDto;
import az.company.card.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author MehdiyevCS on 25.08.21
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/card")
    public Optional<CardDto> getCard(@RequestParam(required = false) Long id,
                                     @RequestParam(required = false) Long orderId) {
        return cardService.getCard(id, orderId);
    }

}
