package zalex14.shelter.entity.report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity: Bot
 *
 */
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "messages")
public class Message {
    /**
     * The message's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The telegram chat id
     */
    @Column(name = "telegram_id")
    private Long telegramId;

    /**
     * The shelter id
     */
    @Column(name = "shelter_id")
    private Long shelterId;

    /**
     * The comment
     */
    @Column(name = "comment")
    private String comment;

    /**
     * The message's date & time
     */
    @Column(name = "message_time")
    private LocalDateTime messageTime;

    /**
     * Is message done (replied)
     */
    @Column(name = "is_reply")
    private Boolean isReply;
}
