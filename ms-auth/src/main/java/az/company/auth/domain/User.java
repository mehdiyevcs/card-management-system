package az.company.auth.domain;

import az.company.auth.domain.enumeration.UserRole;
import az.company.auth.domain.enumeration.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"password"})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private UserState status;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
