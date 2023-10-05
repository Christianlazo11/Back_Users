package com.chris.api.service;

import com.chris.api.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {

    public List<Customer> findAll();
    public Optional<Customer> findById(Long id);
    public Optional<Customer> save(Customer customer);
    public void delete(Long id);

}
