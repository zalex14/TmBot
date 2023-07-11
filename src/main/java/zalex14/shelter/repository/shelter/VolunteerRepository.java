package zalex14.shelter.repository.shelter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zalex14.shelter.entity.shelter.Volunteer;

import java.util.List;

/**
 * Repository: Volunteer
 * Implement report access layer for volunteers
 */
@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    List<Volunteer> findAllByShelterId(Long aLong);
//    @NotNull
//    @Override
//    Page<Volunteer> findAll(@NotNull Pageable pageable);
//    Slice<Volunteer> findAllByShelterId(Long aLong);
}
