package zalex14.shelter.service.pet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import zalex14.shelter.entity.pet.Pet;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.pet.PetRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The mockito tests for pet service
 *
 * @see Pet (Long id; String nickName; int age; boolean isSickness; String description; boolean breed;
 * boolean isAvailable; Integer duration;  Pet.Schema schema)
 * @see PetRepository
 */
@ContextConfiguration(classes = {PetService.class})
@ExtendWith(MockitoExtension.class)
public class PetServiceTest {
    public static Long id = 1L;
    Pet dog = new Pet(1L, "Айбек", 2, false, "особенности собаки", true,
            true, 30, Pet.Schema.SEEKING);
    Pet cat = new Pet(2L, "Гулназ", 3, false, "особенности кошки", false,
            false, 30, Pet.Schema.TRIAL30);
    private final List<Pet> allPet = List.of(dog, cat);

    @Mock
    private PetRepository repositoryMock;

    @InjectMocks
    private PetService out;

    /**
     * Test to create
     */
    @Test
    @DisplayName("Создаёт и возвращает нового питомца")
    void shouldCreateAndReturnNewPet() {
        when(repositoryMock.save(dog)).thenReturn(dog);
        assertEquals(dog, out.create(dog));
        verify(repositoryMock, times(1)).save(dog);
    }

    /**
     * Test to read all
     */
    @Test
    @DisplayName("Возвращает всех питомцев")
    void shouldReadAndReturnAllPet() {
        when(repositoryMock.findAll()).thenReturn(allPet);
        assertEquals(allPet, out.readAll());
        verify(repositoryMock, times(1)).findAll();
    }

    /**
     * Test to read by id
     */
    @Test
    @DisplayName("Возвращает питомца по id")
    void shouldReadAndReturnPetById() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(dog));
        assertEquals(dog, out.readById(id));
        verify(repositoryMock, times(1)).findById(id);
    }

    /**
     * Test to update
     */
    @Test
    @DisplayName("Изменяет и возвращает питомца")
    void shouldUpdateAndReturnPet() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(dog));
        when(repositoryMock.save(any())).thenReturn(dog);
        assertEquals(dog, out.update(id, dog));
        verify(repositoryMock, times(1)).findById(id);
        verify(repositoryMock, times(1)).save(dog);
    }

    /**
     * Test to delete
     */
    @Test
    @DisplayName("Удаляет питомца")
    void shouldDeletePetById() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(dog));
        doNothing().when(repositoryMock).deleteById(id);
        out.delete(id);
        verify(repositoryMock, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Выбрасывает ошибку, что питомец не обновлен")
    void shouldThrowWhenNotUpdatingPet() {
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.update(1L, dog));
        verify(repositoryMock, times(1)).findById(id);
    }

    @Test
    @DisplayName("Выбрасывает ошибку, когда питомец по id не найден")
    void shouldThrowWhenPetNotFound() {
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.readById(id));
        verify(repositoryMock, times(1)).findById(id);
    }
}
