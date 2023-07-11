package zalex14.shelter.service.shelter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import zalex14.shelter.entity.shelter.Volunteer;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.shelter.VolunteerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The mockito tests for the volunteer service
 *
 * @see Volunteer (private Long id; Long telegramId; String telegramUserName;  String fullName;  String info;
 * Long shelterId;)
 * @see VolunteerRepository
 */
@ContextConfiguration(classes = {VolunteerService.class})
@ExtendWith(MockitoExtension.class)
public class VolunteerServiceTest {
    private final Long id = 1L;
    Volunteer volunteer = new Volunteer(1L, 43691132L, "mansur",
            "МАНСУР ШЫНБОЛАТ КАЛЫКУЛЫ", "Призер соревнований", 1L);
    Volunteer volunteer2 = new Volunteer(2L, 44628132L, "hamid",
            "ХАМИДОВ РАМЗАН ИСМАИЛОВИЧ", "Диплом администрации Алматы", 2L);
    private final List<Volunteer> allVolunteer = List.of(volunteer, volunteer2);

    @Mock
    private VolunteerRepository repositoryMock;

    @InjectMocks
    private VolunteerService out;

    /**
     * Test to create
     */
    @Test
    @DisplayName("Создаёт и возвращает нового волонтёра")
    void shouldCreateAndReturnNewVolunteer() {
        when(repositoryMock.save(volunteer)).thenReturn(volunteer);
        assertEquals(volunteer, out.create(volunteer));
        verify(repositoryMock, times(1)).save(volunteer);
    }

    /**
     * Test to read all
     */
    @Test
    @DisplayName("Возвращает всех волонтёров")
    void shouldReadAndReturnAllVolunteers() {
        when(repositoryMock.findAll()).thenReturn(allVolunteer);
        assertEquals(allVolunteer, out.readAll());
        verify(repositoryMock, times(1)).findAll();
    }

    /**
     * Test to read by id
     */
    @Test
    @DisplayName("Возвращает волонтёра при поиске по id")
    void shouldReadAndReturnVolunteerById() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(volunteer));
        assertEquals(volunteer, out.readById(id));
        verify(repositoryMock, times(1)).findById(id);
    }

    /**
     * Test to update
     */
    @Test
    @DisplayName("Изменяет и возвращает волонтёра")
    void shouldUpdateAndReturnVolunteer() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(volunteer));
        when(repositoryMock.save(any())).thenReturn(volunteer);
        assertEquals(volunteer, out.update(id, volunteer));
        verify(repositoryMock, times(1)).findById(id);
        verify(repositoryMock, times(1)).save(volunteer);
    }

    /**
     * Test to delete
     */
    @Test
    @DisplayName("Удаляет волонтёра")
    void shouldDeleteVolunteerById() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(volunteer));
        doNothing().when(repositoryMock).deleteById(id);
        out.delete(id);
        verify(repositoryMock, Mockito.times(1)).deleteById(id);
    }


    @Test
    @DisplayName("Выбрасывает ошибку, что волонтёр не обновлен")
    void shouldThrowWhenNotUpdatingVolunteer() {
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.update(1L, volunteer));
        verify(repositoryMock, times(1)).findById(id);
    }

    @Test
    @DisplayName("Выбрасывает ошибку, когда волонтёр по id не найден")
    void shouldThrowWhenVolunteerNotFound() {
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.readById(id));
        verify(repositoryMock, times(1)).findById(id);
    }
}
