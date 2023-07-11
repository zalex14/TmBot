package zalex14.shelter.repository.shelter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zalex14.shelter.entity.shelter.Shelter;

/**
 * Repository: Shelter
 * Implement report access layer for shelters
 */
@Repository
public interface ShelterRepository  extends JpaRepository<Shelter, Long> {
}
