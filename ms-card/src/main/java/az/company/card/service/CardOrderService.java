package az.company.card.service;

import az.company.card.domain.CardOrder;
import az.company.card.dto.CardOrderDto;
import az.company.card.mapper.CardOrderMapper;
import az.company.card.model.request.CreateCardOrderRequest;
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
    private final Long USER_ID1 = 12345L;

    public List<CardOrderDto> getCardOrders(){
        return cardOrderRepository.findAllByUserId(USER_ID1)
                .stream()
                .map(cardOrderMapper:: toDto)
                .collect(Collectors.toList());
    }

    public Optional<CardOrderDto> getCardOrder(@PathVariable Long id){
        return cardOrderRepository.findByIdAndUserId(id, USER_ID1).map(cardOrderMapper::toDto);
    }

    public CardOrderDto createCardOrder(@RequestBody CardOrderDto cardOrderDto){
        CardOrder cardOrder = cardOrderMapper.toEntity(cardOrderDto);
        cardOrder.setUserId(USER_ID1);
        cardOrder = cardOrderRepository.save(cardOrder);
        return cardOrderMapper.toDto(cardOrder);
    }

    public void editCardOrder(@RequestBody CreateCardOrderRequest cardOrderRequest){
        //need to be implemented
    }

    public void deleteCardOrder(@PathVariable Long id){
        //need to be implemented
    }

    public void submitCardOrder(@PathVariable Long id){
        //need to be implemented
    }

}
