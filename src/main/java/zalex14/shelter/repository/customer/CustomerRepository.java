package zalex14.shelter.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zalex14.shelter.entity.customer.Customer;

import java.util.Optional;

/**
 * Repository: Customer
 * Implement report access layer for users
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByTelegramId(Long aLong);
    Optional<Customer> deleteCustomerByTelegramId(Long aLong);
    Optional<Customer> findCustomerByIsAdopterTrue();
}
