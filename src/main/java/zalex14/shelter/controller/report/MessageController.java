package zalex14.shelter.controller.report;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zalex14.shelter.entity.report.Message;
import zalex14.shelter.service.report.MessageService;

import java.util.List;

/**
 * a REST controller for messages HTTP requests
 */
@RestController
@RequestMapping("/message")
@AllArgsConstructor
public class MessageController {
    private final MessageService service;

    /**
     * Create new message
     *
     * @param message obj
     */
    @PostMapping
    public ResponseEntity<Message> create(@RequestBody Message message) {
        return service.create(message) != null
                ? ResponseEntity.ok(service.create(message))
                : ResponseEntity.notFound().build();
    }

    /**
     * Read all messages
     *
     * @return a list of messages
     */
    @GetMapping
    public ResponseEntity<List<Message>> readAll() {
        return service.readAll() != null
                ? ResponseEntity.ok(service.readAll())
                : ResponseEntity.notFound().build();
    }

    /**
     * Read the message by id
     *
     * @param id The message's id
     * @return message  The message obj
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> readById(@PathVariable("id") Long id) {
        return service.readById(id) != null
                ? ResponseEntity.ok(service.readById(id))
                : ResponseEntity.notFound().build();
    }

    /**
     * The message's update
     *
     * @param id      The message's id
     * @param message The message obj
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Message> update(@PathVariable("id") Long id, @RequestBody Message message) {
        return service.update(id, message) != null
                ? ResponseEntity.ok(service.update(id, message))
                : ResponseEntity.notFound().build();
    }

    /**
     * The message remove
     *
     * @param id The message's id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(id + " deleted", HttpStatus.OK);
    }
}
