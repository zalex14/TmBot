package zalex14.shelter.entity.shelter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "handlers")
public class Handler {
    /**
     * The handler id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The handler's telegram id for call
     */
    @Column(name = "telegram_id")
    private Long telegramId;

    /**
     * The handler's telegram username
     */
    @Column(name = "telegram_username")
    private String telegramUserName;

    /**
     * The handler full name
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * The handler company
     */
    @Column(name = "company")
    private String company;

    /**
     * The handler's info (diploma, prizes, recommendations, etc.)
     */
    @Column(name = "info")
    private String info;

    @Override
    public String toString() {
        return "\n" + id + ") " + fullName + " (фирма: " + company + ") " + ", " + info + "\n" +
                " Позвонить/написать кинологу: https://t.me/" + telegramUserName +  "\n";
    }
}
