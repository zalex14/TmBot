package zalex14.shelter.controller.shelter;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zalex14.shelter.entity.shelter.Handler;
import zalex14.shelter.service.shelter.HandlerService;

import java.util.List;

/**
 * a REST controller for handle HTTP requests
 */
@RestController
@RequestMapping("/handler")
@AllArgsConstructor
public class HandlerController {
    private final HandlerService service;

    /**
     * Create new handler
     *
     * @param handler obj
     */
    @PostMapping
    public ResponseEntity<Handler> create(@RequestBody Handler handler) {
        Handler created = service.create(handler);
        return created != null
                ? ResponseEntity.ok().body(created)
                : ResponseEntity.notFound().build();
    }

    /**
     * Read all handlers
     *
     * @return a list of handlers
     */
    @GetMapping
    public ResponseEntity<List<Handler>> readAll() {
        return service.readAll() != null
                ? ResponseEntity.ok(service.readAll())
                : ResponseEntity.notFound().build();
    }

    /**
     * Read handler by id
     *
     * @param id The handler's id
     * @return The handler obj
     */
    @GetMapping("/{id}")
    public ResponseEntity<Handler> readById(@PathVariable("id") Long id) {
        return service.readAll() != null
                ? ResponseEntity.ok(service.readById(id))
                : ResponseEntity.notFound().build();
    }

    /**
     * The handler's update
     *
     * @param handler The handler obj
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Handler> update(@PathVariable("id") Long id, @RequestBody Handler handler) {
        return service.update(id, handler) != null
                ? ResponseEntity.ok(service.update(id, handler))
                : ResponseEntity.badRequest().build();
    }

    /**
     * The handler delete
     *
     * @param id The handler's id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(id + " deleted", HttpStatus.OK);
    }
}
