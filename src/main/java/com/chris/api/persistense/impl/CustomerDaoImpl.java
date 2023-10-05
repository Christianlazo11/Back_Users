package com.chris.api.persistense.impl;

import com.chris.api.entities.Customer;
import com.chris.api.persistense.ICustomerDao;
import com.chris.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerDaoImpl implements ICustomerDao {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> save(Customer customer) {
        return Optional.of(customerRepository.save(customer));
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
