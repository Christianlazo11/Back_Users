package com.chris.api.persistense;
import com.chris.api.entities.Customer;
import java.util.List;
import java.util.Optional;

public interface ICustomerDao{

    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    Optional<Customer> save(Customer customer);
    void delete(Long id);
}
