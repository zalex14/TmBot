package zalex14.shelter.controller.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zalex14.shelter.entity.report.Report;
import zalex14.shelter.service.report.ReportService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    ReportService service;
    private final Long id = 1L;
    Report report = new Report(1L, LocalDateTime.of(2023, 5, 14, 11, 45),
            "Рацион животного", "Общее самочувствие хорошее", "Изменения в поведении",
            1L, 1L, 1L);
    Report report2 = new Report(2L, LocalDateTime.of(2023, 4, 13, 12, 48),
            "Поедание комнатных растений", "отличное", "Изменения в поведении", 2L,
            2L, 2L);
    private final List<Report> reports = List.of(report, report2);
    public static final String UPDATE = """
            [ {
                "id": 0,
                "reportTime": "2023-05-08T19:55:06.924Z",
                "diet": "string",
                "health": "string",
                "behavior": "string",
                "customerId": 0,
                "petId": 0,
                "photoId": 0
              } ]
            """;
    /**
     * Post test to create
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldPostJsonNewReportAndCreate() throws Exception {
        String json = mapper.writeValueAsString(report);

        when(service.create(any())).thenReturn(report);
        mockMvc.perform(
                        post("/report")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(service, times(2)).create(any());
    }

    /**
     * Get test to read all
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldGetAllReportsAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(reports);

        when(service.readAll()).thenReturn(reports);
        mockMvc.perform(
                        get("/report"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(service, times(2)).readAll();
    }

    /**
     * Get test to read by id
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldGetReportByIdAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(report);

        when(service.readById(id)).thenReturn(report);
        mockMvc.perform(
                        get("/report/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(service, times(2)).readById(id);
    }

    /**
     * Patch test to update
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldPatchJsonAndUpdateReport() throws Exception {
        String json = mapper.writeValueAsString(report);
        when(service.update(any(), any(Report.class))).thenReturn(report);
        mockMvc.perform(
                        patch("/report/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(id.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(service, times(2)).update(id, report);
    }

    /**
     * Delete test to delete
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldDeleteReportByIdAndReturnDeleted() throws Exception {
        doNothing().when(service).delete(id);
        mockMvc.perform(delete("/report/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(id + " deleted"));
        verify(service, times(1)).delete(id);
    }
}
