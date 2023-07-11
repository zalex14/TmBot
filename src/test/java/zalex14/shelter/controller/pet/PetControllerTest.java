package zalex14.shelter.controller.pet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zalex14.shelter.entity.pet.Pet;
import zalex14.shelter.entity.shelter.Volunteer;
import zalex14.shelter.service.pet.PetService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
@WebMvcTest(PetController.class)
@ExtendWith(MockitoExtension.class)
public class PetControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    PetService service;
    private final Long id = 1L;
    Pet dog = new Pet(1L, "Айбек", 2, false, "Особенности собаки", true,
            true, 30, Pet.Schema.SEEKING);
    Pet cat = new Pet(2L, "Гулназ", 3, false, "особенности кошки", false,
            false, 30, Pet.Schema.TRIAL30);
    List<Pet> pets = List.of(dog, cat);
    public static final String UPDATE = """
            [  {
                "id": 7,
                "nickName": "Аиза",
                "age": 7,
                "description": "любит кит-и-кэт",
                "breed": false,
                "duration": null,
                "schema": null,
                "available": true,
                "sickness": false
              } ]
            """;
    /**
     * Post test to create
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldPostJsonNewPetAndCreate() throws Exception {
        String json = mapper.writeValueAsString(dog);

        when(service.create(any())).thenReturn(dog);
        mockMvc.perform(
                        post("/pet")
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
    public void shouldGetAllPetsAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(pets);

        when(service.readAll()).thenReturn(pets);
        mockMvc.perform(
                        get("/pet"))
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
    public void shouldGetPetByIdAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(dog);

        when(service.readById(id)).thenReturn(dog);
        mockMvc.perform(
                        get("/pet/" + id))
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
    public void shouldPatchJsonAndUpdatePet() throws Exception {
        String json = mapper.writeValueAsString(dog);
        when(service.update(any(), any(Pet.class))).thenReturn(dog);
        mockMvc.perform(
                        patch("/pet/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(id.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(service, times(2)).update(id, dog);
    }

    /**
     * Delete test to delete
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldDeletePetByIdAndReturnDeleted() throws Exception {
        doNothing().when(service).delete(id);
        mockMvc.perform(delete("/pet/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(id + " deleted"));
        verify(service, times(1)).delete(id);
    }
}
