package zalex14.shelter.service.report;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import zalex14.shelter.entity.report.Message;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.report.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * The mockito tests for message service
 *
 * @see Message (Long id; Long chatId; Long shelterId; String comment; LocalDateTime messageTime; Boolean isReply)
 * @see MessageRepository
 */
@ContextConfiguration(classes = {MessageService.class})
@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    public static Long id = 1L;
    Message message = new Message(1L, 45634134L, 1L, "Тестовое сообщение 1",
            LocalDateTime.of(2023, 5, 14, 11, 45), false);
    Message message2 = new Message(2L, 43534189L, 2L, "Тестовое сообщение 2",
            LocalDateTime.of(2023, 4, 1, 9, 45), true);
    private final List<Message> allMessage = List.of(message, message2);

    @Mock
    private MessageRepository repositoryMock;

    @InjectMocks
    private MessageService out;

    /**
     * Test to create
     */
    @Test
    @DisplayName("Создаёт и возвращает новое сообщение")
    void shouldCreateAndReturnNewMessage() {
        when(repositoryMock.save(message)).thenReturn(message);
        assertEquals(message, out.create(message));
        verify(repositoryMock, times(1)).save(message);
    }

    /**
     * Test to read all
     */
    @Test
    @DisplayName("Возвращает всех сообщения")
    void shouldReadAndReturnAllMessage() {
        when(repositoryMock.findAll()).thenReturn(allMessage);
        assertEquals(allMessage, out.readAll());
        verify(repositoryMock, times(1)).findAll();
    }

    /**
     * Test to read by id
     */
    @Test
    @DisplayName("Возвращает сообщение по id")
    void shouldReadAndReturnMessageById() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(message));
        assertEquals(message, out.readById(id));
        verify(repositoryMock, times(1)).findById(id);
    }

    /**
     * Test to update
     */
    @Test
    @DisplayName("Изменяет и возвращает сообщение")
    void shouldUpdateAndReturnMessage() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(message));
        when(repositoryMock.save(any())).thenReturn(message);
        assertEquals(message, out.update(id, message));
        verify(repositoryMock, times(1)).findById(id);
        verify(repositoryMock, times(1)).save(message);
    }

    /**
     * Test to delete
     */
    @Test
    @DisplayName("Удаляет сообщение")
    void shouldDeleteMessageById() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(message));
        doNothing().when(repositoryMock).deleteById(id);
        out.delete(id);
        verify(repositoryMock, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Выбрасывает ошибку, что сообщение не обновлено")
    void shouldThrowWhenNotUpdatingMessage() {
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.update(1L, message));
        verify(repositoryMock, times(1)).findById(id);
    }

    @Test
    @DisplayName("Выбрасывает ошибку, когда сообщение по id не найдено")
    void shouldThrowWhenMessageNotFound() {
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.readById(id));
        verify(repositoryMock, times(1)).findById(id);
    }

    @Test
    @DisplayName("Возвращает сообщения клиента с id")
    void shouldReadAndReturnMessageByCustomerId() {
        when(repositoryMock.findAllByTelegramId(id)).thenReturn(allMessage);
        assertEquals(allMessage, out.readByChatId(id));
        verify(repositoryMock, times(1)).findAllByTelegramId(id);
    }
}
