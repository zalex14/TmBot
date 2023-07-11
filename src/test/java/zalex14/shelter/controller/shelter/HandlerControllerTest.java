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
import zalex14.shelter.entity.shelter.Handler;
import zalex14.shelter.service.shelter.HandlerService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HandlerController.class)
@ExtendWith(MockitoExtension.class)
public class HandlerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    HandlerService service;
    private final Long id = 1L;
    Handler handler = new Handler(1L, 43691132L, "mansur",
            "МАНСУР ШЫНБОЛАТ КАЛЫКУЛЫ", "Дом ЦВМ", "Призер соревнований");
    Handler handler2 = new Handler(2L, 44628132L, "hamid",
            "ХАМИДОВ РАМЗАН ИСМАИЛОВИЧ", "Алтын ит", "Диплом администрации Алматы");
    private final List<Handler> handlers = List.of(handler, handler2);
    public static final String UPDATE = """
            {
                "id": 3,
                "telegramId": 597689091,
                "telegramUserName": "silly_Ejevika",
                "fullName": "МАРАСУЛОВ БАХРОМЖОН ЮНУСОВИЧ",
                "company": "Alfa dog",
                "info": "Грамота президента"
            }
            """;
    /**
     * Post test to create
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldPostJsonNewHandlerAndCreate() throws Exception {
        String json = mapper.writeValueAsString(handler);

        when(service.create(any())).thenReturn(handler);
        mockMvc.perform(
                        post("/handler")
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
    public void shouldGetAllHandlersAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(handlers);

        when(service.readAll()).thenReturn(handlers);
        mockMvc.perform(
                        get("/handler"))
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
    public void shouldGetHandlerByIdAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(handler);

        when(service.readById(id)).thenReturn(handler);
        mockMvc.perform(
                        get("/handler/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(service, times(1)).readById(id);
    }

    /**
     * Patch test to update
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldPatchJsonAndUpdateHandler() throws Exception {
        String json = mapper.writeValueAsString(handler);
        when(service.update(any(), any(Handler.class))).thenReturn(handler);
        mockMvc.perform(
                        patch("/handler/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(id.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(service, times(2)).update(id, handler);
    }

    /**
     * Delete test to delete
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldDeleteByIdAndDelete() throws Exception {
        doNothing().when(service).delete(id);
        mockMvc.perform(delete("/handler/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(id + " deleted"));
        verify(service, times(1)).delete(id);
    }
}
