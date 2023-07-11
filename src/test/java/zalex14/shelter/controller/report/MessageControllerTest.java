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
import zalex14.shelter.entity.report.Message;
import zalex14.shelter.service.report.MessageService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @see  Long id; Long telegramId; Long shelterId; String comment; LocalDateTime messageTime; Boolean isReply;
 */
@WebMvcTest(MessageController.class)
@ExtendWith(MockitoExtension.class)
public class MessageControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    MessageService service;
    private final Long id = 1L;
    Message message = new Message(1L, 45634134L, 1L, "Тестовое сообщение 1",
            LocalDateTime.of(2023, 4, 14, 11, 45), false);
    Message message2 = new Message(2L, 43534189L, 2L, "Тестовое сообщение 2",
            LocalDateTime.of(2023, 4, 13, 12, 48), true);
    private final List<Message> messages = List.of(message, message2);

    /**
     * Post test to create
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldPostJsonNewMessageAndCreate() throws Exception {
        String json = mapper.writeValueAsString(message);

        when(service.create(any())).thenReturn(message);
        mockMvc.perform(
                        post("/message")
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
    public void shouldGetAllMessagesAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(messages);

        when(service.readAll()).thenReturn(messages);
        mockMvc.perform(
                        get("/message"))
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
    public void shouldGetMessageByIdAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(message);

        when(service.readById(id)).thenReturn(message);
        mockMvc.perform(
                        get("/message/" + id))
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
    public void shouldPatchJsonAndUpdateMessage() throws Exception {
        String json = mapper.writeValueAsString(message);
        when(service.update(any(), any(Message.class))).thenReturn(message);
        mockMvc.perform(
                        patch("/message/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(id.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(service, times(2)).update(id, message);
    }

    /**
     * Delete test to delete
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldDeleteMessageByIdAndReturnDeleted() throws Exception {
        doNothing().when(service).delete(id);
        mockMvc.perform(delete("/message/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(id + " deleted"));
        verify(service, times(1)).delete(id);
    }
}
