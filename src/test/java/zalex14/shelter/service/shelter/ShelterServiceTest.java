package zalex14.shelter.service.shelter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import zalex14.shelter.entity.shelter.Shelter;
import zalex14.shelter.repository.shelter.ShelterRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The mockito tests for the shelter service
 *
 * @see Shelter ( Long id; Long telegramId; String title; String information; String address; Float latitude;
 * Float longitude; String phone; String workingHours;  String territoryAdmission; String territoryStaying;
 * String securityContacts; String petAcquaintance; String documentList; String travelRecommendation;
 * String childArranging; String adultArranging; String sickArrangement; String handlerAdvice; String refusalReason;)
 * @see ShelterRepository
 */
@ContextConfiguration(classes = {ShelterService.class})
@ExtendWith(MockitoExtension.class)
public class ShelterServiceTest {
    public final Long id = 2L;
    Shelter shelter = new Shelter(1L, 40456732L, "Жибек жолы",
            "Общественный фонд «Жибек жолы»", "улица Кунаева, 62, Алматы",
            43.262654f, 76.949651f, "8 771 388-8111", "Ежедневно 10:00–18:00",
            "правила пропуска на территорию", "правила нахождения в приюте",
            "8 771 388-3121", "правила общения с собаками",
            "список документов для усыновления", "рекомендации по транспортировке",
            "рекомендации по обустройству щеноков", "рекомендации по обустройству собак",
            "обустройство и уход собак с заболеваниями", "советы кинолога",
            "возможные причины отказа в усыновлении");
    Shelter shelter2 = new Shelter(2L, 65148379L, "Ак-Босар",
            "Благотворительный фонд «Ак-Босар» - инициативная группа", "Новая ул., 58, Костанай",
            53.216128f, 63.718504f, "8 705 467-7363", "Круглосуточно",
            "правила пропуска на территорию", "правила нахождения в приюте",
            "8 705 467-2374", "правила общения с кошками",
            "список документов для усыновления", "рекомендации по транспортировке",
            "рекомендации по обустройству котят", "рекомендации по обустройству кошек",
            "обустройство и уход котов с заболеваниями", "",
            "возможные причины отказа в усыновлении");
    public final String SHELTER_NAME = "Приют Кошкин дом";
    public final String SHELTER_INFO = "Приют бездомных питомцев для костанайцев с добрыми сердцами!";
    private final List<Shelter> shelters = List.of(shelter, shelter2);

    @Mock
    private ShelterRepository repositoryMock;
    @InjectMocks
    private ShelterService out;

    /**
     * Test to create
     */
    @Test
    void shouldCreateAndReturnNewShelter() {
        when(repositoryMock.save(shelter)).thenReturn(shelter);
        assertEquals(shelter, out.create(shelter));
        verify(repositoryMock, times(1)).save(shelter);

        assertEquals(shelter.getId(), out.create(shelter).getId());
        assertEquals(shelter.getTitle(), out.create(shelter).getTitle());
        assertEquals(shelter.getInformation(), out.create(shelter).getInformation());
        assertEquals(shelter.getAddress(), out.create(shelter).getAddress());
        assertEquals(shelter.getPhone(), out.create(shelter).getPhone());
        assertEquals(shelter.getLatitude(), out.create(shelter).getLatitude());
        assertEquals(shelter.getTelegramId(), out.create(shelter).getTelegramId());
    }

    /**
     * Test to read all
     */
    @Test
    void shouldReadAndReturnAllShelters() {
        when(repositoryMock.findAll()).thenReturn(shelters);
        assertEquals(shelters, out.readAll());
        verify(repositoryMock, times(1)).findAll();
    }

    /**
     * Test to read by id
     */
    @Test
    @DisplayName("Возвращает приют по id")
    void shouldReadAndReturnShelterById() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(shelter));
        assertEquals(shelter, out.readById(id));
        verify(repositoryMock, times(1)).findById(id);
    }

    /**
     * Test to update
     */
    @Test
    void shouldUpdateAndReturnShelter() {
        when(repositoryMock.save(any())).thenReturn(shelter);
        when(repositoryMock.findById(any())).thenReturn(Optional.of(shelter));

        Shelter shelter1 = new Shelter();
        shelter1.setId(id);
        shelter1.setTitle(SHELTER_NAME);
        shelter1.setInformation(SHELTER_INFO);

        assertSame(shelter, out.update(id, shelter1));
        verify(repositoryMock).save(any());
        verify(repositoryMock).findById(any());
    }

    /**
     * Test to delete
     */
    @Test
    @DisplayName("Удаляет приют")
    void shouldDeleteShelterById() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(shelter));
        doNothing().when(repositoryMock).deleteById(id);
        out.delete(id);
        verify(repositoryMock, Mockito.times(1)).deleteById(id);
    }
}
