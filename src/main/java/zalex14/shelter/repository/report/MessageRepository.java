package zalex14.shelter.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zalex14.shelter.entity.report.Message;

import java.util.List;

/**
 * Repository: Bot messages to volunteers
 * Implement bot access layer for the telegram
 */
@Repository
public interface MessageRepository  extends JpaRepository<Message, Long> {
    List<Message> findAllByTelegramId(Long aLong);
}
