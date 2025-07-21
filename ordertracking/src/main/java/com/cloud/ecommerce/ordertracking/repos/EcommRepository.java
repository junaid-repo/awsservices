package com.cloud.ecommerce.ordertracking.repos;

import com.cloud.ecommerce.ordertracking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcommRepository extends JpaRepository<Customer, Long> {
}
