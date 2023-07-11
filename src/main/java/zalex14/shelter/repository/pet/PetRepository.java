package zalex14.shelter.repository.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zalex14.shelter.entity.pet.Pet;

/**
 * Repository: Cat
 * Implement report access layer for cats
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
