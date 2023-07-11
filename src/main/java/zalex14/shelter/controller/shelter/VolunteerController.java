package zalex14.shelter.controller.shelter;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zalex14.shelter.entity.shelter.Volunteer;
import zalex14.shelter.service.shelter.VolunteerService;

import java.util.List;

/**
 * a REST controller for volunteer HTTP requests
 *
 * @see Volunteer (Long id; Long telegramId; String telegramUserName;  String fullName;  String info; Long shelterId)
 * @see VolunteerService CRUD operations
 */
@RestController
@RequestMapping("/volunteer")
@AllArgsConstructor
public class VolunteerController {
    private final VolunteerService service;

    /**
     * create new volunteer
     *
     * @param volunteer obj
     */
    @PostMapping
    public ResponseEntity<Volunteer> create(@RequestBody Volunteer volunteer) {
        Volunteer created = service.create(volunteer);
        return created != null
                ? ResponseEntity.ok().body(created)
                : ResponseEntity.badRequest().build();
    }

    /**
     * Read all volunteers
     *
     * @return a list of volunteers
     */
    @GetMapping
    public ResponseEntity<List<Volunteer>> readAll() {
        return service.readAll() != null
                ? ResponseEntity.ok(service.readAll())
                : ResponseEntity.notFound().build();
    }

    /**
     * Read volunteer by id
     *
     * @param id The volunteer's id
     * @return The volunteer obj
     */
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> readById(@PathVariable("id") Long id) {
        return service.readById(id) != null
                ? ResponseEntity.ok(service.readById(id))
                : ResponseEntity.notFound().build();
    }

    /**
     * The volunteer's update
     *
     * @param id        The volunteer's id
     * @param volunteer The volunteer obj
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Volunteer> update(@PathVariable("id") Long id, @RequestBody Volunteer volunteer) {
        return service.update(id, volunteer) != null
                ? ResponseEntity.ok(service.update(id, volunteer))
                : ResponseEntity.notFound().build();
    }

    /**
     * The volunteer's delete
     *
     * @param id The volunteer's id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(id + " deleted", HttpStatus.OK);
    }
}
