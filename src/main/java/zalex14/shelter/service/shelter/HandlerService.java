package zalex14.shelter.service.shelter;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zalex14.shelter.entity.shelter.Handler;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.shelter.HandlerRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Handler
 * Service for managing the recommended handlers for dogs
 *
 * @see Handler (Long id; Long telegramId; String telegramUserName; String fullName; String company; String info;)
 * @see HandlerRepository
 */
@Service
@Data
@RequiredArgsConstructor
public class HandlerService {

    private final HandlerRepository repository;

    /**
     * Create handler
     *
     * @param handler The new handler
     */
    public Handler create(Handler handler) {
        return repository.save(handler);
    }

    /**
     * Read all handlers
     *
     * @return The list of handlers
     */
    public List<Handler> readAll() {
        return repository.findAll();
    }

    /**
     * Read handler by id
     *
     * @param id the daily handler's id
     * @return The handler obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Handler readById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(" Кинолог с id " + id + " не найден"));
    }

    /**
     * Update the handler by id
     *
     * @param id the handler id
     * @param handler the handler obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Handler update(Long id, Handler handler) {
        Handler updated = repository.findById(id).orElseThrow(() -> new NotFoundException(" Кинолог с id " + id +
                " не найдено"));
        updated.setTelegramId(handler.getTelegramId());
        updated.setTelegramUserName(handler.getTelegramUserName());
        updated.setFullName(handler.getFullName());
        updated.setCompany(handler.getCompany());
        updated.setInfo(handler.getInfo());
        return repository.save(updated);
    }

    /**
     * Delete the handler by id
     *
     * @param id the handler's id
     */
    public void delete(Long id) {
        Optional.of(repository.findById(id)).ifPresent(handler -> repository.deleteById(id));
    }
}
