package zalex14.shelter.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zalex14.shelter.entity.report.Report;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository: Report
 * Implement report access layer for daily reports
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findReportByPetId(Long aLong);
    List<Report> findByCustomerId(Long aLong);
    List<Report> findByReportTimeAfterAndPetId(LocalDateTime reportTime, Long Id);
}
