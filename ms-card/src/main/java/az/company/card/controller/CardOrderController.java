package az.company.card.controller;

import az.company.card.dto.CardOrderDto;
import az.company.card.service.CardOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author MehdiyevCS on 22.08.21
 */
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
    public void getCardOrder(@PathVariable Long id) {
        cardOrderService.getCardOrder(id);
    }

    @PostMapping("/create")
    public void createCardOrder(@RequestBody CardOrderDto cardOrderDto) {
        cardOrderService.createCardOrder(cardOrderDto);
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
    public void submitCardOrder(@PathVariable Long id) {
        cardOrderService.submitCardOrder(id);
    }

}
