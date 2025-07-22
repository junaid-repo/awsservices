package com.cloud.ecommerce.ordertracking.repos;

import com.cloud.ecommerce.ordertracking.entity.Customer;
import com.cloud.ecommerce.ordertracking.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}
