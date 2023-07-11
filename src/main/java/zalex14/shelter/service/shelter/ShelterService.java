package zalex14.shelter.service.shelter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zalex14.shelter.entity.shelter.Shelter;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.shelter.ShelterRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Shelter
 * Service for managing the shelter information
 *
 * @see Shelter ( Long id; Long telegramId; String title; String information; String address; Float latitude;
 * Float longitude; String phone; String workingHours;  String territoryAdmission; String territoryStaying;
 * String securityContacts; String petAcquaintance; String documentList; String travelRecommendation;
 * String childArranging; String adultArranging; String sickArrangement; String handlerAdvice; String refusalReason;)
 * @see ShelterRepository
 */
@Service
@RequiredArgsConstructor
public class ShelterService {
    private final ShelterRepository repository;

    /**
     * Create shelter
     *
     * @param shelter The new shelter
     */
    public Shelter create(Shelter shelter) {
        return repository.save(shelter);
    }

    /**
     * Read all shelters
     *
     * @return The list of shelters
     */
    public List<Shelter> readAll() {
        return repository.findAll();
    }

    /**
     * Read shelter by id
     *
     * @param id the shelter's id
     * @return The shelter obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Shelter readById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(" Приют с id " + id + " не найден"));
    }

    /**
     * Update the shelter by id
     *
     * @param id the shelter's id
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Shelter update(Long id, Shelter shelter) {
        Shelter updated = repository.findById(id).orElseThrow(() -> new NotFoundException(" Приют c id " + id
                + " не найден"));
        updated.setTitle(shelter.getTitle());
        updated.setInformation(shelter.getInformation());
        updated.setAddress(shelter.getAddress());
        updated.setLatitude(shelter.getLatitude());
        updated.setLongitude(shelter.getLongitude());
        updated.setPhone(shelter.getPhone());
        updated.setWorkingHours(shelter.getWorkingHours());
        updated.setTerritoryAdmission(shelter.getTerritoryAdmission());
        updated.setTerritoryStaying(shelter.getTerritoryStaying());
        updated.setSecurityContacts(shelter.getSecurityContacts());
        updated.setPetAcquaintance(shelter.getPetAcquaintance());
        updated.setDocumentList(shelter.getDocumentList());
        updated.setTravelRecommendation(shelter.getTravelRecommendation());
        updated.setChildArranging(shelter.getChildArranging());
        updated.setAdultArranging(shelter.getAdultArranging());
        updated.setSickArrangement(shelter.getSickArrangement());
        updated.setHandlerAdvice(shelter.getHandlerAdvice());
        updated.setRefusalReason(shelter.getRefusalReason());
        return repository.save(updated);
    }

    /**
     * Delete the shelter by id
     *
     * @param id the shelter's id
     */
    public void delete(Long id) {
        Optional.of(repository.findById(id)).ifPresent(shelter -> repository.deleteById(id));
    }
}
