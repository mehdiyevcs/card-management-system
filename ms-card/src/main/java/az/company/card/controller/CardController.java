package az.company.card.controller;

import az.company.card.dto.CardDto;
import az.company.card.error.model.ErrorResponse;
import az.company.card.service.CardService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request",
                    response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error",
                    response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized",
                    response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden",
                    response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found",
                    response = ErrorResponse.class),
    })
    @GetMapping("/card")
    public Optional<CardDto> getCard(@RequestParam(required = false) Long id,
                                     @RequestParam(required = false) Long orderId) {
        return cardService.getCard(id, orderId);
    }

}
