package az.company.customer.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Data
@Entity
@Table(name = "az/company/customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "pin", nullable = false)
    private String pin;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
