package zalex14.shelter.controller.pet;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zalex14.shelter.entity.pet.Pet;
import zalex14.shelter.service.pet.PetService;

import java.util.List;

/**
 * a REST controller for pets HTTP requests
 */
@RestController
@RequestMapping("/pet")
@AllArgsConstructor
public class PetController {
    private final PetService service;

    /**
     * Create new pet
     *
     * @param pet obj
     */
    @PostMapping
    public ResponseEntity<Pet> create(@RequestBody Pet pet) {
        return service.create(pet) != null
                ? ResponseEntity.ok(service.create(pet))
                : ResponseEntity.notFound().build();
    }

    /**
     * Read all pets
     *
     * @return a list of pets
     */
    @GetMapping
    public ResponseEntity<List<Pet>> readAll() {
        return service.readAll() != null
                ? ResponseEntity.ok(service.readAll())
                : ResponseEntity.notFound().build();
    }

    /**
     * Read pet by id
     *
     * @param id The pet's id
     * @return pet The pet obj
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pet> readById(@PathVariable("id") Long id) {
        return service.readAll() != null
                ? ResponseEntity.ok(service.readById(id))
                : ResponseEntity.notFound().build();
    }

    /**
     * The pet's update
     *
     * @param id  The pet's id
     * @param pet The pet obj
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Pet> update(@PathVariable("id") Long id, @RequestBody Pet pet) {
        return service.update(id, pet) != null
                ? ResponseEntity.ok(service.update(id, pet))
                : ResponseEntity.notFound().build();
    }

    /**
     * The pet delete
     *
     * @param id The pet's id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(id + " deleted", HttpStatus.OK);
    }
}
