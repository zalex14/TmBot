package zalex14.shelter.controller.shelter;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zalex14.shelter.entity.shelter.Shelter;
import zalex14.shelter.service.shelter.ShelterService;

import java.util.List;

/**
 * a REST controller for shelter  HTTP requests
 *
 */
@RestController
@RequestMapping("/shelter")
@AllArgsConstructor
public class ShelterController {
    private final ShelterService service;

    /**
     * Create new shelter
     *
     * @param shelter obj
     */
    @PostMapping
    public ResponseEntity<Shelter> create(@RequestBody Shelter shelter) {
        return service.create(shelter) != null
                ? ResponseEntity.ok(service.create(shelter))
                : ResponseEntity.notFound().build();
    }

    /**
     * Read all shelters
     *
     * @return a list of shelters
     */
    @GetMapping
    public ResponseEntity<List<Shelter>> readAll() {
        return service.readAll() != null
                ? ResponseEntity.ok(service.readAll())
                : ResponseEntity.notFound().build();
    }

    /**
     * Read shelter by id
     *
     * @param id        The shelter's id
     * @return shelter  The shelter obj
     */
    @GetMapping("/{id}")
    public ResponseEntity<Shelter> readById(@PathVariable("id") Long id) {
        return service.readById(id) != null
                ? ResponseEntity.ok(service.readById(id))
                : ResponseEntity.notFound().build();
    }

    /**
     * The shelter's update
     *
     * @param id      The shelter's id
     * @param shelter The shelter obj
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Shelter> update(@PathVariable("id") Long id, @RequestBody Shelter shelter) {
        return service.update(id, shelter) != null
                ? ResponseEntity.ok(service.update(id, shelter))
                : ResponseEntity.notFound().build();
    }


    /**
     * The shelter delete
     *
     * @param id The shelter's id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(id + " deleted", HttpStatus.OK);
    }
}
