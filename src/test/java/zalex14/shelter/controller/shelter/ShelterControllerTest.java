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
import zalex14.shelter.entity.shelter.Shelter;
import zalex14.shelter.service.shelter.ShelterService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShelterController.class)
@ExtendWith(MockitoExtension.class)
public class ShelterControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    ShelterService service;
    private final Long id = 1L;
    Shelter shelter = new Shelter(1L, 40456732L, "Жибек жолы",
            "Общественный фонд «Жибек жолы» - это благотворительная организация",
            "улица Кунаева, 62, Алматы", 43.262654f, 76.949651f, "8 771 388-8111",
            "Ежедневно 10:00–18:00", "правила пропуска на территорию",
            "правила нахождения в приюте", "8 771 388-3121",
            "правила общения с собаками", "список документов для усыновления",
            "рекомендации по транспортировке", "рекомендации по обустройству щеноков",
            "рекомендации по обустройству собак", "обустройство и уход собак с заболеваниями",
            "советы кинолога", "возможные причины отказа в усыновлении");
    Shelter shelter2 = new Shelter(2L, 65148379L, "Ак-Босар",
            "Благотворительный фонд «Ак-Босар» - инициативная группа","Новая ул., 58, Костанай",
            53.216128f, 63.718504f, "8 705 467-7363", "Круглосуточно",
            "правила пропуска на территорию", "правила нахождения в приюте",
            "8 705 467-2374", "правила общения с кошками",
            "список документов для усыновления","рекомендации по транспортировке",
            "рекомендации по обустройству котят", "рекомендации по обустройству кошек",
            "обустройство и уход котов с заболеваниями", "",
            "возможные причины отказа в усыновлении");
    private final List<Shelter> shelters = List.of(shelter, shelter2);

    public static final String UPDATE = """
            {
               "id": 1,
                "telegramId": 1459742726,
                "title": "Жибек жолы",
                "information": "Общественный фонд «Жибек жолы» - это благотворительная организация",
                "address": "улица Кунаева, 62, Алматы",
                "latitude": 43.262653,
                "longitude": 76.94965,
                "phone": "+7 (771) 388 8111",
                "workingHours": "Ежедневно 10:00–18:00",
                "territoryAdmission": "При посещении Приюта необходимо иметь документ удостоверяющий личность",
                "territoryStaying": "На территории Приюта для посетителей действуют правила и распорядок",
                "securityContacts": "+7 (771) 388-3121",
                "petAcquaintance": "Не делайте резких движений, не издавайте громких звуков.",
                "documentList": "В договоре фиксируются данные обеих сторон, оговариваются пункты содержания",
                "travelRecommendation": "Рекомендации по транспортировке",
                "childArranging": "Рекомендации по обустройству щенков",
                "adultArranging": "Для собачки следует привезти поводок с ошейником. Не мешайте животному",
                "sickArrangement": "Собаки из приютов слабо социализированы, зачастую пугливы.",
                "handlerAdvice": "Если вы хотите найти игривого компаньона, собака из питомника – не для вас.",
                "refusalReason": "Возможные причины отказа в усыновлении"
            }
            """;
    /**
     * Post test to create
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldPostJsonNewShelterAndCreate() throws Exception {
        String json = mapper.writeValueAsString(shelter);

        when(service.create(any())).thenReturn(shelter);
        mockMvc.perform(
                        post("/shelter")
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
    public void shouldGetAllSheltersAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(shelters);

        when(service.readAll()).thenReturn(shelters);
        mockMvc.perform(
                        get("/shelter"))
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
    public void shouldGetShelterByIdAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(shelter);

        when(service.readById(id)).thenReturn(shelter);
        mockMvc.perform(
                        get("/shelter/" + id))
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
    public void shouldPatchJsonAndUpdateShelter() throws Exception {
        String json = mapper.writeValueAsString(shelter);
        when(service.update(any(), any(Shelter.class))).thenReturn(shelter);
        mockMvc.perform(
                        patch("/shelter/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(id.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(service, times(2)).update(id, shelter);
    }

    /**
     * Delete test to delete
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldDeleteByIdAndDelete() throws Exception {
        doNothing().when(service).delete(id);
        mockMvc.perform(delete("/shelter/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(id + " deleted"));
        verify(service, times(1)).delete(id);
    }
}
