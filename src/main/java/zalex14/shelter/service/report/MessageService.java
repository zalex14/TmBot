package zalex14.shelter.service.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zalex14.shelter.entity.report.Message;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.report.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service for the bot message to a volunteer
 *
 * @see Message (Long id; Long chatId; Long shelterId; String comment; LocalDateTime messageTime; Boolean isReply;)
 */
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;

    /**
     * Create new message
     *
     * @param message obj
     */
    public Message create(Message message) {
        return repository.save(message);
    }

    /**
     * Read all messages
     *
     * @return message list
     */
    public List<Message> readAll() {
        return this.repository.findAll();
    }

    /**
     * Read message by id
     *
     * @return message obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Message readById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(" Сообщение № " + id + " не найдено"));
    }

    /**
     * Update of message's data
     *
     * @param id      message's id
     * @param message obj
     * @throws NotFoundException Calls method NotFoundException(message) if message with id didn't exist
     */
    public Message update(Long id, Message message) {
        Message updated = repository.findById(id).orElseThrow(() -> new NotFoundException(" Обновляемое сообщение " +
                "с id " + id + " не найдено"));
        updated.setTelegramId(message.getTelegramId());
        updated.setShelterId(message.getShelterId());
        updated.setComment(message.getComment());
        updated.setMessageTime(LocalDateTime.now());
        updated.setIsReply(message.getIsReply());
        return repository.save(updated);
    }

    /**
     * Delete of message by id
     *
     * @param id message's id
     */
    public void delete(Long id) {
        Optional.of(repository.findById(id)).ifPresent(message -> repository.deleteById(id));
    }

    /**
     * Read of a message for chatId
     *
     * @return message obj
     */
    public List<Message> readByChatId(Long chatId) {
        return repository.findAllByTelegramId(chatId);
    }

}
