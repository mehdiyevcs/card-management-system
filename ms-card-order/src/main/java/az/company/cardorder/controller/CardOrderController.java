package az.company.cardorder.controller;

import az.company.cardorder.dto.CardOrderDto;
import az.company.cardorder.service.CardOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
@RestController
@RequestMapping("/api/card/order")
@RequiredArgsConstructor
@Validated
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
    public CardOrderDto createCardOrder(@Valid @RequestBody CardOrderDto cardOrderDto) {
        return cardOrderService.createCardOrder(cardOrderDto);
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
