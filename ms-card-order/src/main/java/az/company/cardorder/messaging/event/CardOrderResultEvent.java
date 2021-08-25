package az.company.cardorder.messaging.event;

import az.company.cardorder.domain.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CardOrderResultEvent implements Serializable {
    private Long id;
    private OrderStatus orderStatus;
    private String description;
}
