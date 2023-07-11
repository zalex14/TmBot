package zalex14.shelter.service.report;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import zalex14.shelter.entity.report.Report;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.report.ReportRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Report
 * Service for managing daily adopter's report
 *
 * @see Report (Long id; LocalDateTime reportTime; String diet; String health; String behavior; Long customerId;
 * Long petId; Long photoId;)
 * @see ReportRepository
 */
@EnableScheduling
@Service
@Data
@RequiredArgsConstructor
public class ReportService {
    private ReportRepository repository;

    /**
     * Create daily report
     *
     * @param report The new daily report
     */
    public Report create(Report report) {
        return repository.save(report);
    }

    /**
     * Read all reports
     *
     * @return The list of reports
     */
    public List<Report> readAll() {
        return repository.findAll();
    }

    /**
     * Read report by id
     *
     * @param id the daily report's id
     * @return The report obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Report readById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(" Не найден отчет с id " + id));
    }

    /**
     * Update the report by id
     *
     * @param id the daily report's id
     * @return The report obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Report update(Long id, Report report) {
        Report updated = repository.findById(id).orElseThrow(() -> new NotFoundException(" Отчет " +
                "с id " + id + " не найден"));
        updated.setReportTime(LocalDateTime.now());
        updated.setDiet(report.getDiet());
        updated.setHealth(report.getHealth());
        updated.setBehavior(report.getBehavior());
        updated.setCustomerId(report.getCustomerId());
        updated.setPetId(report.getPetId());
        updated.setPhotoId(report.getPhotoId());
        return repository.save(updated);
    }

    /**
     * Delete the report by id
     *
     * @param id the daily report's id
     */
    public void delete(Long id) {
        Optional.of(repository.findById(id)).ifPresent(report -> repository.deleteById(id));
    }

    /**
     * Read report by pet's id
     *
     * @param petId pet's id
     */
    public List<Report> readByPetId(Long petId) {
        return repository.findReportByPetId(petId);
    }

    /**
     * Read report by customer's id
     *
     * @param customerId customer's id
     */
    public List<Report> readByCustomerId(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    /**
     * Read report after reportTime
     *
     * @param reportTime reportTime
     * @param petId      pet's id
     */
    public List<Report> readByReportTimeAndPetId(LocalDateTime reportTime, Long petId) {
        return repository.findByReportTimeAfterAndPetId(reportTime, petId);
    }
}
