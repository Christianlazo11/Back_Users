package com.chris.api.service.impl;

import com.chris.api.persistense.ICustomerDao;
import com.chris.api.entities.Customer;
import com.chris.api.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDao customerDao;

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerDao.findById(id);
    }

    @Override
    public Optional<Customer> save(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public void delete(Long id) {
        customerDao.delete(id);
    }
}
