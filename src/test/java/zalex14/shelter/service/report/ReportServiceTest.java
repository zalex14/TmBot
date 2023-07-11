package zalex14.shelter.service.report;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zalex14.shelter.entity.report.Report;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.report.ReportRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * The mockito tests for report service
 *
 * @see Report (Long id; LocalDateTime reportTime; String diet; String health; String behavior; Long customerId;
 * Long petId; Long photoId;)
 * @see ReportRepository
 */
@ContextConfiguration(classes = {ReportService.class})
@ExtendWith(SpringExtension.class)
public class ReportServiceTest {
    public static Long id = 1L;
    public static LocalDateTime reportDateTime = LocalDateTime.now();
    Report report = new Report(1L, LocalDateTime.of(2023, 5, 14, 11, 45),
            "Рацион животного", "Общее самочувствие хорошее", "Изменения в поведении",
            1L, 1L, 1L);
    Report report2 = new Report(2L, LocalDateTime.now(),
            "Поедание комнатных растений", "Общее самочувствие отличное", "Осваивает новый дом",
            2L, 2L, 2L);
    private final List<Report> allReports = List.of(report, report2);

    @Mock
    private ReportRepository repositoryMock;
    @InjectMocks
    private ReportService out;

    /**
     * Test to create
     */
    @Test
    @DisplayName("Создаёт и возвращает новый отчет")
    void shouldCreateAndReturnNewReport() {
        when(repositoryMock.save(report)).thenReturn(report);
        assertEquals(report, out.create(report));
        verify(repositoryMock, times(1)).save(report);
    }

    /**
     * Test to read all
     */
    @Test
    @DisplayName("Возвращает все отчеты")
    void shouldReadAndReturnAllReports() {
        when(repositoryMock.findAll()).thenReturn(allReports);
        assertEquals(allReports, out.readAll());
        verify(repositoryMock, times(1)).findAll();
    }

    /**
     * Test to read by id
     */
    @Test
    @DisplayName("Возвращает отчет по id")
    void shouldReadAndReturnReportById() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(report));
        assertEquals(report, out.readById(id));
        verify(repositoryMock, times(1)).findById(id);
    }

    /**
     * Test to update
     */
    @Test
    @DisplayName("Изменяет и возвращает отчет")
    void shouldUpdateAndReturnReport() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(report));
        when(repositoryMock.save(any())).thenReturn(report);
        assertEquals(report, out.update(id, report));
        verify(repositoryMock, times(1)).findById(id);
        verify(repositoryMock, times(1)).save(report);
    }

    /**
     * Test to delete
     */
    @Test
    @DisplayName("Удаляет отчет")
    void shouldDeleteReportById() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(report));
        doNothing().when(repositoryMock).deleteById(id);
        out.delete(id);
        verify(repositoryMock, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Выбрасывает ошибку, что отчет не обновлен")
    void shouldThrowWhenNotUpdatingReport() {
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.update(1L, report));
        verify(repositoryMock, times(1)).findById(id);
    }

    @Test
    @DisplayName("Выбрасывает ошибку, когда отчет по id не найден")
    void shouldThrowWhenReportNotFound() {
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.readById(id));
        verify(repositoryMock, times(1)).findById(id);
    }

    /**
     * Test to read by pet's id
     */
    @Test
    @DisplayName("Возвращает отчеты питомца с id")
    void shouldReadAndReturnReportsByPetId() {
        when(repositoryMock.findReportByPetId(id)).thenReturn(allReports);
        assertEquals(allReports, out.readByPetId(id));
        verify(repositoryMock, times(1)).findReportByPetId(id);
    }

    /**
     * Test to read by customer's id
     */
    @Test
    @DisplayName("Возвращает отчеты усыновителя с id")
    void shouldReadAndReturnReportsByCustomerId() {
        when(repositoryMock.findByCustomerId(id)).thenReturn(allReports);
        assertEquals(allReports, out.readByCustomerId(id));
        verify(repositoryMock, times(1)).findByCustomerId(id);
    }

    /**
     * Test to read by customer's id
     */
    @Test
    @DisplayName("Возвращает отчеты питомца c даты и времени")
    void shouldReadAndReturnReportsByTimeAndPet() {
        when(repositoryMock.findByReportTimeAfterAndPetId(reportDateTime, id)).thenReturn(allReports);
        assertEquals(allReports, out.readByReportTimeAndPetId(reportDateTime, id));
        verify(repositoryMock, times(1)).findByReportTimeAfterAndPetId(reportDateTime, id);
    }
}
