package zalex14.shelter.controller.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zalex14.shelter.entity.customer.Customer;
import zalex14.shelter.service.customer.CustomerService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @see Long id; String fullName; Long telegramChatId; String telegramUserName; String phone; Boolean isAdopter;
 * String comment
 */
@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    CustomerService service;
    private final Long id = 1L;
    Customer customer = new Customer(1L, "НИЯЗОВ ТУРАЛ САБУХИ", 1445698132L,
            "diaz98", false, "/start");
    Customer customer2 = new Customer(2L, "БАКИТЖАНОВ РУШАТ ЗИНУРУЛЫ", 1445676133L,
            "rush13", false,"по рекомендации");
    private final List<Customer> customers = List.of(customer, customer2);
    public static final String UPDATE = """
            [  {
                "id": 4,
                "fullName": "САИТОВА НИЛЮФАР ШАВКАТОВНА",
                "telegramChatId": 1445678135,
                "telegramUserName": "saitova7",
                "phone": "77773821377",
                "isAdopter": false,
                "comment": "НИЛЮФАР ШАВКАТОВНА"
              } ]
            """;
    /**
     * Post test to create
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldPostJsonNewCustomerAndCreate() throws Exception {
        String json = mapper.writeValueAsString(customer);

        when(service.create(any())).thenReturn(customer);
        mockMvc.perform(
                        post("/customer")
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
    public void shouldGetAllCustomersAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(customers);

        when(service.readAll()).thenReturn(customers);
        mockMvc.perform(
                        get("/customer"))
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
    public void shouldGetCustomerByIdAndReadJson() throws Exception {
        String json = mapper.writeValueAsString(customer);

        when(service.readById(id)).thenReturn(customer);
        mockMvc.perform(
                        get("/customer/" + id))
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
    public void shouldPatchJsonAndUpdateCustomer() throws Exception {
        String json = mapper.writeValueAsString(customer);
        when(service.update(any(), any(Customer.class))).thenReturn(customer);
        mockMvc.perform(
                        patch("/customer/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(id.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(service, times(2)).update(id, customer);
    }

    /**
     * Delete test to delete
     *
     * @throws Exception Calls method NotFoundException(message)
     */
    @Test
    public void shouldDeleteCustomerByIdAndReturnDeleted() throws Exception {
        doNothing().when(service).delete(id);
        mockMvc.perform(delete("/customer/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(id + " deleted"));
        verify(service, times(1)).delete(id);
    }
}
