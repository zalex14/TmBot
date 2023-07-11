package zalex14.shelter.controller.customer;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zalex14.shelter.entity.customer.Customer;
import zalex14.shelter.service.customer.CustomerService;

import java.util.List;

/**
 * a REST controller for customers HTTP requests
 */
@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService service;

    /**
     * Create new customer
     *
     * @param customer obj
     */
    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return service.create(customer) != null
                ? ResponseEntity.ok(service.create(customer))
                : ResponseEntity.notFound().build();
    }

    /**
     * Read all customers
     *
     * @return a list of customers
     */
    @GetMapping
    public ResponseEntity<List<Customer>> readAll() {
        return service.readAll() != null
                ? ResponseEntity.ok(service.readAll())
                : ResponseEntity.notFound().build();
    }

    /**
     * Read the customer by id
     *
     * @param id The customer's id
     * @return customer The customer obj
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> readById(@PathVariable("id") Long id) {
        return service.readById(id) != null
                ? ResponseEntity.ok(service.readById(id))
                : ResponseEntity.notFound().build();
    }

    /**
     * The customer's update
     *
     * @param id       The customer's id
     * @param customer The customer obj
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable("id") Long id, @RequestBody Customer customer) {
        return service.update(id, customer) != null
                ? ResponseEntity.ok(service.update(id, customer))
                : ResponseEntity.notFound().build();
    }

    /**
     * The customer delete
     *
     * @param id The customer's id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(id + " deleted", HttpStatus.OK);
    }

    /**
     * Read active adopters
     *
     * @return the adopter list
     */
    @GetMapping("/get/adopter/")
    public Customer readAdopters() {
        return service.readAdopter();
    }
}
