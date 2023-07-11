package zalex14.shelter.repository.shelter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zalex14.shelter.entity.shelter.Handler;

/**
 * Repository: Handler
 * Implement report access layer for recommended dog's handlers
 */
@Repository
public interface HandlerRepository extends JpaRepository<Handler, Long> {
}
