package zalex14.shelter.service.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ContextConfiguration;
import zalex14.shelter.entity.customer.Customer;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.customer.CustomerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

/**
 * The mockito tests for customer service
 *
 * @see Customer (Long id; String fullName; Long telegramChatId; String telegramUserName; String phone;
 * Boolean isAdopter; String comment)
 * @see CustomerRepository
 */
@ContextConfiguration(classes = {CustomerService.class})
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    public static Long id = 1L;

    Customer customer = new Customer(1L, "НИЯЗОВ ТУРАЛ САБУХИ", 1445698132L,
            "diaz98", false, "/start");
    Customer customer2 = new Customer(2L, "БАКИТЖАНОВ РУШАТ ЗИНУРУЛЫ", 1445676133L,
            "rush13", false,"по рекомендации");
    private final List<Customer> allCustomer = List.of(customer, customer2);

    @Mock
    private CustomerRepository repositoryMock;

    @InjectMocks
    private CustomerService out;

    /**
     * Test to create
     */
    @Test
    @DisplayName("Создаёт и возвращает нового клиента")
    void shouldCreateAndReturnNewCustomer() {
        when(repositoryMock.save(customer)).thenReturn(customer);
        assertEquals(customer, out.create(customer));
        verify(repositoryMock, times(1)).save(customer);
    }

    /**
     * Test to read all
     */
    @Test
    @DisplayName("Возвращает всех клиентов")
    void shouldReadAndReturnAllCustomers() {
        when(repositoryMock.findAll()).thenReturn(allCustomer);
        assertEquals(allCustomer, out.readAll());
        verify(repositoryMock, times(1)).findAll();
    }

    /**
     * Test to read by id
     */
    @Test
    @DisplayName("Возвращает клиента по id")
    void shouldReadAndReturnCustomer() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(customer));
        assertEquals(customer, out.read(id));
        verify(repositoryMock, times(1)).findById(id);
    }

    /**
     * Test to read by telegram id
     */
    @Test
    @DisplayName("Возвращает клиента по Telegram id")
    void shouldReadAndReturnCustomerById() {
        when(repositoryMock.findByTelegramId(id)).thenReturn(Optional.of(customer));
        assertEquals(customer, out.readById(id));
        verify(repositoryMock, times(1)).findByTelegramId(id);
    }

    /**
     * Test to update
     */
    @Test
    @DisplayName("Изменяет и возвращает клиента")
    void shouldUpdateAndReturnCustomer() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(customer));
        when(repositoryMock.save(any())).thenReturn(customer);
        assertEquals(customer, out.update(id, customer));
        verify(repositoryMock, times(1)).findById(id);
        verify(repositoryMock, times(1)).save(customer);
    }

    /**
     * Test to delete
     */
    @Test
    @DisplayName("Удаляет клиента по id")
    void shouldDeleteCustomerById() {
        when(repositoryMock.findById(id)).thenReturn(Optional.of(customer));
        doNothing().when(repositoryMock).deleteById(id);
        out.deleteById(id);
        verify(repositoryMock, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Возвращает активных усыновитей")
    void shouldReadAndReturnActiveAdopters() {
        when(repositoryMock.findCustomerByIsAdopterTrue()).thenReturn(Optional.of(customer));
        assertEquals(customer, out.readAdopter());
        verify(repositoryMock, times(1)).findCustomerByIsAdopterTrue();
    }

    @Test
    @DisplayName("Выбрасывает ошибку при обновлении клиента")
    void shouldThrowWhenNotUpdatingCustomer() {
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.update(1L, customer));
        verify(repositoryMock, times(1)).findById(id);
    }

    @Test
    @DisplayName("Выбрасывает ошибку при чтении клиента")
    void shouldThrowWhenCustomerNotRead() {
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.read(id));
        verify(repositoryMock, times(1)).findById(id);
    }

    @Test
    @DisplayName("Выбрасывает ошибку при чтении клиента по telegram id")
    void shouldThrowWhenCustomerNotReadByTelegramId() {
        when(repositoryMock.findByTelegramId(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.readById(id));
        verify(repositoryMock, times(1)).findByTelegramId(id);
    }

    @Test
    @DisplayName("Выбрасывает ошибку при чтении активных усыновителей")
    void shouldThrowWhenActiveAdopterNotRead() {
        when(repositoryMock.findCustomerByIsAdopterTrue()).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> out.readAdopter());
        verify(repositoryMock, times(1)).findCustomerByIsAdopterTrue();
    }

    @Test
    @DisplayName("Проверка существвания клиента")
    void shouldTrueWhenCustomerExist() {
        ExampleMatcher customerMatcher = ExampleMatcher.matching().withIgnorePaths("chatId")
                .withMatcher("telegramChatId", ignoreCase());
        Customer customer = new Customer();
        customer.setTelegramId(id);
        when(repositoryMock.exists(Example.of(customer, customerMatcher))).thenReturn(true);
        assertTrue(out.isCustomerExist(id));
        verify(repositoryMock, times(1)).exists(Example.of(customer, customerMatcher));
    }
}
