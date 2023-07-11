package zalex14.shelter.service.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zalex14.shelter.entity.pet.Pet;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.pet.PetRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service for pets
 *
 * @see Pet (Long id; String nickName; int age; boolean isSickness; String description; boolean breed;
 * boolean isAvailable; Integer duration;  Pet.Schema schema;)
 * @see PetRepository
 */
@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository repository;

    /**
     * Creat pet
     *
     * @param pet obj
     */

    public Pet create(Pet pet) {
        return repository.save(pet);
    }

    /**
     * Read all pets
     *
     * @return pet's list
     */
    public List<Pet> readAll() {
        return this.repository.findAll();
    }

    /**
     * Read pet by id
     *
     * @return pet obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Pet readById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(" Питомец с id " + id + " не найден"));
    }

    /**
     * Update of pet's data
     *
     * @param id  pet's id
     * @param pet obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Pet update(Long id, Pet pet) {
        Pet updated = repository.findById(id).orElseThrow(() -> new NotFoundException(" Питомец " +
                "с id " + id + " не найден"));
        updated.setNickName(pet.getNickName());
        updated.setAge(pet.getAge());
        updated.setSickness(pet.isSickness());
        updated.setDescription(pet.getDescription());
        updated.setBreed(pet.isBreed());
        updated.setAvailable(pet.isAvailable());
        updated.setDuration(pet.getDuration());
        updated.setSchema(pet.getSchema());
        return repository.save(pet);
    }

    /**
     * Delete of pet by id
     *
     * @param id pet's id
     */
    public void delete(Long id) {
        Optional.of(repository.findById(id)).ifPresent(pet -> repository.deleteById(id));
    }
}
