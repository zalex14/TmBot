package zalex14.shelter.entity.shelter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Entity: Volunteers
 */
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "volunteers")
public class Volunteer {
    /**
     * The volunteer's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The volunteer's telegram id
     */
    @Column(name = "telegram_id")
    private Long telegramId;

    /**
     * The handler's telegram username
     */
    @Column(name = "telegram_username")
    private String telegramUserName;

    /**
     * The volunteer's full name
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * The volunteer's info
     */
    @Column(name = "info")
    private String info;

    /**
     * The volunteer's shelter id
     */
    @Column(name = "shelter_id")
    private Long shelterId;
}
