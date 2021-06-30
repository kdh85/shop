package com.exam.shop.repository;

import com.exam.shop.domain.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long>, OrdersRepositoryCustom {
}
