package zalex14.shelter.entity.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Entity: Customer
 *
 */
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "customers")
public class Customer {
    /**
     * The customer's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OrderBy
    @Column(name = "id")
    private Long id;

    /**
     * The customer's full name
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * The customer's telegram id
     */
    @Column(name = "telegram_id")
    private Long telegramId;

    /**
     * The customer's telegram username
     */
    @Column(name = "telegram_username")
    private String telegramUserName;

    /**
     * Is adopter of pet
     */
    @Column(name = "adopter")
    private Boolean isAdopter = false;

    /**
     * The customer's comment
     */
    @Column(name = "comment")
    private String comment;
}
