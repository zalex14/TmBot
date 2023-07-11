package zalex14.shelter.controller.shelter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zalex14.shelter.entity.shelter.Volunteer;
import zalex14.shelter.service.shelter.VolunteerService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VolunteerController.class)
@ExtendWith(MockitoExtension.class)
public class VolunteerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    VolunteerService service;

    private final Long id = 1L;
    Volunteer volunteer = new Volunteer(1L, 43691132L, "mars",
            "МАНСУР ШЫНБОЛАТ КАЛЫКУЛЫ", "Призер соревнований", 1L);
    Volunteer volunteer2 = new Volunteer(2L, 44628132L, "hamid",
            "ХАМИДОВ РАМЗАН ИСМАИЛОВИЧ", "Диплом администрации Алматы", 2L);
    private final List<Volunteer> volunteers = List.of(volunteer, volunteer2);

    public static final String UPDATE = """
            {
                "id": 1,
                "telegramId": 43691132,
                "telegramUserName": "mars",
                "fullName": "МАНСУР ШЫНБОЛАТ КАЛЫКУЛЫ",
                "info": "Призер соревнований",
                "shelterId": 1
            }
            """;

    /**
     * Post test to create
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldPostJsonNewVolunteerAndCreate() throws Exception {
        String json = mapper.writeValueAsString(volunteer);

        when(service.create(any())).thenReturn(volunteer);
        mockMvc.perform(
                        post("/volunteer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(service, times(1)).create(any());
    }

    /**
     * Get test to read all
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldGetAllVolunteersAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(volunteers);

        when(service.readAll()).thenReturn(volunteers);
        mockMvc.perform(
                        get("/volunteer"))
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
    public void shouldGetVolunteerByIdAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(volunteer);

        when(service.readById(id)).thenReturn(volunteer);
        mockMvc.perform(
                        get("/volunteer/" + id))
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
    public void shouldPatchJsonAndUpdateVolunteer() throws Exception {
        String json = mapper.writeValueAsString(volunteer);
        when(service.update(any(), any(Volunteer.class))).thenReturn(volunteer);
        mockMvc.perform(
                        patch("/volunteer/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(id.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(service, times(2)).update(id, volunteer);
    }

    /**
     * Delete test to delete
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldDeleteByIdAndDelete() throws Exception {
        doNothing().when(service).delete(id);
        mockMvc.perform(delete("/volunteer/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(id + " deleted"));
        verify(service, times(1)).delete(id);
    }
}
