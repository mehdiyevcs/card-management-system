package az.company.card.service;

import az.company.card.domain.enumeration.OrderStatus;
import az.company.card.dto.CardOrderDto;
import az.company.card.mapper.CardOrderMapper;
import az.company.card.repository.CardOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MehdiyevCS on 22.08.21
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CardOrderService {

    private final CardOrderRepository cardOrderRepository;
    private final CardOrderMapper cardOrderMapper;

    //UserId will be extracted from ContextHolder
    private final static Long USER_ID1 = 12345L;

    public List<CardOrderDto> getCardOrders() {
        return cardOrderRepository.findAllByUserId(USER_ID1)
                .stream()
                .map(cardOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CardOrderDto> getCardOrder(@PathVariable Long id) {
        return cardOrderRepository.findByIdAndUserId(id, USER_ID1).map(cardOrderMapper::toDto);
    }

    public CardOrderDto createCardOrder(@RequestBody CardOrderDto cardOrderDto) {
        var cardOrder = cardOrderMapper.toEntity(cardOrderDto);
        cardOrder.setUserId(USER_ID1);

        //Here will be insert to Operation table

        cardOrder = cardOrderRepository.save(cardOrder);
        return cardOrderMapper.toDto(cardOrder);
    }

    public CardOrderDto editCardOrder(@RequestBody CardOrderDto cardOrderDto) {
        var cardOrder = cardOrderRepository.findById(cardOrderDto.getId())
                .orElseThrow(() -> new RuntimeException("Not Found"));

        if (cardOrder.getStatus() == OrderStatus.SUBMITTED) {
            throw new RuntimeException("Order can not be edited");
        }
        cardOrder.setStatus(OrderStatus.EDITED);
        cardOrder = cardOrderRepository.save(cardOrder);
        return cardOrderMapper.toDto(cardOrder);
    }

    public CardOrderDto deleteCardOrder(@PathVariable Long id) {
        var cardOrder = cardOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found"));

        //Here will be insert to Operation table (For History)

        if (cardOrder.getStatus() == OrderStatus.SUBMITTED) {
            throw new RuntimeException("Order can not be deleted");
        }

        cardOrderRepository.delete(cardOrder);
        return cardOrderMapper.toDto(cardOrder);
    }

    public CardOrderDto submitCardOrder(@PathVariable Long id) {
        var cardOrder = cardOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found"));

        //Here will be insert to Operation table (For History)

        if (cardOrder.getStatus() == OrderStatus.SUBMITTED) {
            throw new RuntimeException("Order is already submitted");
        }
        cardOrder.setStatus(OrderStatus.SUBMITTED);
        cardOrder = cardOrderRepository.save(cardOrder);

        //Kafka Event publishing, need to be implemented

        return cardOrderMapper.toDto(cardOrder);
    }

}
