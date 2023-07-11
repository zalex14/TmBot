package zalex14.shelter.controller.report;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zalex14.shelter.entity.report.Report;
import zalex14.shelter.service.report.ReportService;

import java.util.List;

/**
 * a REST controller for daily reports HTTP requests
 *
 */

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {
    private final ReportService service;

    /**
     * Create new report
     *
     * @param report obj
     */
    @PostMapping
    public ResponseEntity<Report> create(@RequestBody Report report) {
        return service.create(report) != null
                ? ResponseEntity.ok(service.create(report))
                : ResponseEntity.notFound().build();
    }

    /**
     * Read all reports
     *
     * @return a list of reports
     */
    @GetMapping
    public ResponseEntity<List<Report>> readAll() {
        return service.readAll() != null
                ? ResponseEntity.ok(service.readAll())
                : ResponseEntity.notFound().build();
    }

    /**
     * Read the report's by id
     *
     * @param id The report's id
     * @return report The report obj
     */
    @GetMapping("/{id}")
    public ResponseEntity<Report> readById(@PathVariable("id") Long id) {
        return service.readById(id) != null
                ? ResponseEntity.ok(service.readById(id))
                : ResponseEntity.notFound().build();
    }

    /**
     * The report's update
     *
     * @param id     The report's id
     * @param report The report obj
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Report> update(@PathVariable("id") Long id, @RequestBody Report report) {
        return service.update(id, report) != null
                ? ResponseEntity.ok(service.update(id, report))
                : ResponseEntity.notFound().build();
    }

    /**
     * The reports delete
     *
     * @param id The report's id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(id + " deleted", HttpStatus.OK);
    }
}
