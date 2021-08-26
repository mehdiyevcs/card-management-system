package az.company.cardorder.controller;

import az.company.cardorder.dto.CardOrderDto;
import az.company.cardorder.error.model.ErrorResponse;
import az.company.cardorder.model.CreateCardOrderRequest;
import az.company.cardorder.service.CardOrderService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author MehdiyevCS on 22.08.21
 */
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
@RestController
@RequestMapping("/api/card/order")
@RequiredArgsConstructor
public class CardOrderController {

    private final CardOrderService cardOrderService;

    @GetMapping
    public List<CardOrderDto> getCardOrders() {
        return cardOrderService.getCardOrders();
    }

    @GetMapping("/{id}")
    public Optional<CardOrderDto> getCardOrder(@PathVariable Long id) {
        return cardOrderService.getCardOrder(id);
    }

    @PostMapping("/create")
    public CardOrderDto createCardOrder(@Valid @RequestBody CreateCardOrderRequest createCardOrderRequest) {
        return cardOrderService.createCardOrder(createCardOrderRequest);
    }

    @PutMapping("/edit")
    public CardOrderDto editCardOrder(@RequestBody CardOrderDto cardOrderDto) {
        return cardOrderService.editCardOrder(cardOrderDto);
    }

    @PutMapping("/{id}/delete")
    public CardOrderDto deleteCardOrder(@PathVariable Long id) {
        return cardOrderService.deleteCardOrder(id);
    }

    @PutMapping("/{id}/submit")
    public CardOrderDto submitCardOrder(@PathVariable Long id) {
        return cardOrderService.submitCardOrder(id);
    }

}
