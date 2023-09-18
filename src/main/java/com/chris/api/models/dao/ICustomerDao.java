package com.chris.api.models.dao;

import com.chris.api.models.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerDao extends JpaRepository<Customer, Long> {

}
