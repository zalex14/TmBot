package zalex14.shelter.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import zalex14.shelter.entity.customer.Customer;
import zalex14.shelter.exception.NotFoundException;
import zalex14.shelter.repository.customer.CustomerRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

/**
 * Service layer for Customer
 *
 * @see Customer (Long id; String fullName; Long telegramChatId; String telegramUserName; String phone;
 * Boolean isAdopter = false; String comment)
 * @see CustomerRepository
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    /**
     * Create customer
     *
     * @param customer obj
     * @return customer obj
     */
    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    /**
     * Read all customers
     *
     * @return customer list
     */
    public List<Customer> readAll() {
        return repository.findAll();
    }

    /**
     * Read customer by id
     *
     * @param id customer id
     * @return customer obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Customer read(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException(" Клиент id " + id + " не найден"));
    }

    /**
     * Read customer by telegram id
     *
     * @param chatId telegram id
     * @return customer obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Customer readById(Long chatId) {
        return repository.findByTelegramId(chatId).orElseThrow(() ->
                new NotFoundException(" Клиент с Telegram chatId " + chatId + " не найден"));
    }

    /**
     * Update of customer
     *
     * @param chatId   telegram id
     * @param customer obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Customer update(Long chatId, Customer customer) {
        Customer updated = repository.findById(chatId).orElseThrow(() -> new NotFoundException(" Клиент " +
                "с Telegram chatId " + chatId + " не найден"));
        updated.setFullName(customer.getFullName());
        updated.setTelegramId(customer.getTelegramId());
        updated.setTelegramUserName(customer.getTelegramUserName());
        updated.setIsAdopter(customer.getIsAdopter());
        updated.setComment(customer.getComment());
        return repository.save(customer);
    }

    /**
     * Delete customer by id
     *
     * @param id customer id
     */
    public void deleteById(Long id) {
        Optional.of(repository.findById(id)).ifPresent(pet -> repository.deleteById(id));
    }

    /**
     * Delete of customer by telegram id
     *
     * @param chatId telegram id
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public void delete(Long chatId) {
        repository.deleteCustomerByTelegramId(chatId).orElseThrow(() -> new NotFoundException(" Клиент " +
                "с Telegram chatId " + chatId + " не найден"));
    }

    /**
     * Read active adopters
     *
     * @return customer obj
     * @throws NotFoundException Calls method NotFoundException(message)
     */
    public Customer readAdopter() {
        return repository.findCustomerByIsAdopterTrue().orElseThrow(() ->
                new NotFoundException(" Активные усыновители не найдены"));
    }

    /**
     * Is customer exist
     *
     * @param chatId of customer telegram chat  id
     */
    public boolean isCustomerExist(Long chatId) {
        ExampleMatcher customerMatcher = ExampleMatcher.matching().withIgnorePaths("chatId")
                .withMatcher("telegramChatId", ignoreCase());
        Customer customer = new Customer();
        customer.setTelegramId(chatId);
        Example<Customer> example = Example.of(customer, customerMatcher);
        return repository.exists(example);
    }
}
