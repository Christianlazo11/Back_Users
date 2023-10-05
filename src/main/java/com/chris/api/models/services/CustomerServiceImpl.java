package com.chris.api.models.services;

import com.chris.api.models.dao.ICustomerDao;
import com.chris.api.models.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerServiceImpl implements ICustomerService{

    @Autowired
    private ICustomerDao customerDao;

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerDao.findById(id).orElse(null);
    }

    @Override
    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public void delete(Long id) {
        customerDao.deleteById(id);
    }
}
