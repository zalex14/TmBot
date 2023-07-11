package zalex14.shelter.service.shelter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zalex14.shelter.entity.shelter.Volunteer;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.shelter.VolunteerRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service for Volunteers
 * Service for managing the volunteer's data
 *
 * @see Volunteer (Long id; Long telegramId; String telegramUserName;  String fullName;  String info; Long shelterId)
 * @see VolunteerRepository
 */
@Service
@AllArgsConstructor
public class VolunteerService {
    private final VolunteerRepository repository;

    /**
     * Create volunteer
     *
     * @param volunteer The new volunteer
     */
    public Volunteer create(Volunteer volunteer) {
        return repository.save(volunteer);
    }

    /**
     * Read all volunteers
     *
     * @return The list of all volunteers
     */
    public List<Volunteer> readAll() {
//        Pageable pageable = PageRequest.of(0, 5, Sort.by("shelterId").ascending()
//                .and(Sort.by("fullName").descending()));
        return repository.findAll();
    }

    /**
     * Read volunteer by id
     *
     * @param id the volunteer's id
     * @return The volunteer obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Volunteer readById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(" Волонтер c id " + id + " не найден"));
    }

    /**
     * Update the volunteer by id
     *
     * @param id the volunteer's id
     * @param volunteer the volunteer obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Volunteer update(Long id, Volunteer volunteer) {
        Volunteer updated = repository.findById(id).orElseThrow(() -> new NotFoundException(" Волонтер " + id
                + " не найден"));
        updated.setTelegramId(volunteer.getTelegramId());
        updated.setTelegramUserName(volunteer.getTelegramUserName());
        updated.setFullName(volunteer.getFullName());
        updated.setInfo(volunteer.getInfo());
        updated.setShelterId(volunteer.getShelterId());
        return repository.save(updated);
    }

    /**
     * Delete the volunteer by id
     *
     * @param id the volunteer's id
     */
    public void delete(Long id) {
        Optional.of(repository.findById(id)).ifPresent(volunteer -> repository.deleteById(id));
    }
}
