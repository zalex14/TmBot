package zalex14.shelter.service.shelter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import zalex14.shelter.entity.shelter.Handler;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.shelter.HandlerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * Mockito test for handler service
 *
 * @see Handler (Long id; Long telegramId; String telegramUserName; String fullName; String company; String info;)
 * @see HandlerRepository
 */
@ContextConfiguration(classes = {HandlerService.class})
@ExtendWith(MockitoExtension.class)
public class HandlerServiceTest {
    private final Long id = 1L;
    Handler handler = new Handler(1L, 43691132L, "mansur",
            "МАНСУР ШЫНБОЛАТ КАЛЫКУЛЫ", "Дом ЦВМ", "Призер соревнований");
    Handler handler2 = new Handler(2L, 44628132L, "hamid",
            "ХАМИДОВ РАМЗАН ИСМАИЛОВИЧ", "Алтын ит", "Диплом администрации Алматы");
    private final List<Handler> allHandler = List.of(handler, handler2);

    @Mock
    private HandlerRepository repositoryMock;

    @InjectMocks
    private HandlerService out;

    /**
     * Test to create
     */
    @Test
    @DisplayName("Создаёт и возвращает нового кинолога")
    void shouldCreateAndReturnNewHandler() {
        when(repositoryMock.save(handler)).thenReturn(handler);
        assertEquals(handler, out.create(handler));
        verify(repositoryMock, times(1)).save(handler);
    }

    /**
     * Test to read all
     */
    @Test
    @DisplayName("Возвращает всех кинологов")
    void shouldReadAndReturnAllHandlers() {
        when(repositoryMock.findAll()).thenReturn(allHandler);
        assertEquals(allHandler, out.readAll());
        verify(repositoryMock, times(1)).findAll();
    }

    /**
     * Test to read by id
     */
    @Test
    @DisplayName("Возвращает кинолога по id")
    void shouldReadAndReturnHandlerById() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(handler));
        assertEquals(handler, out.readById(id));
        verify(repositoryMock, times(1)).findById(id);
    }

    /**
     * Test to update
     */
    @Test
    @DisplayName("Изменяет и возвращает кинолога")
    void shouldUpdateAndReturnHandler() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(handler));
        when(repositoryMock.save(any())).thenReturn(handler);
        assertEquals(handler, out.update(id, handler));
        verify(repositoryMock, times(1)).findById(id);
        verify(repositoryMock, times(1)).save(handler);
    }

    /**
     * Test to delete
     */
    @Test
    @DisplayName("Удаляет кинолога")
    void shouldDeleteHandlerById() {
        doNothing().when(repositoryMock).deleteById(id);
        out.delete(id);
        verify(repositoryMock, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Выбрасывает ошибку, что кинолог не обновлен")
    void shouldThrowWhenNotUpdatingVolunteer() {
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.update(1L, handler));
        verify(repositoryMock, times(1)).findById(id);
    }

    @Test
    @DisplayName("Выбрасывает ошибку, когда кинолог по id не найден")
    void shouldThrowWhenHandlerNotFound() {
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.readById(id));
        verify(repositoryMock, times(1)).findById(id);
    }
}
