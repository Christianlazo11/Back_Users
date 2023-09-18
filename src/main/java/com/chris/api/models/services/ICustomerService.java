package com.chris.api.models.services;

import com.chris.api.models.entity.Customer;

import java.util.List;

public interface ICustomerService {

    public List<Customer> findAll();
}
