package az.company.cardorder.domain;

import az.company.cardorder.domain.enumeration.CardType;
import az.company.cardorder.domain.enumeration.OrderStatus;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author MehdiyevCS on 22.08.21
 */
@Entity
@Table(name = "card_order")
@Data
public class CardOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType cardType;

    @NotNull
    @Column(name = "card_holder_full_name", nullable = false)
    private String cardHolderFullName;

    @NotNull
    @Column(name = "period", nullable = false)
    private Integer period;

    @Column(name = "urgent")
    private boolean urgent;

    @NotNull
    @Column(name = "code_word", nullable = false)
    private String codeWord;

    @NotNull
    @Column(name = "card_holder_pin", nullable = false)
    private String cardHolderPin;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "username", nullable = false)
    private String username;
}
