package com.example.erp.domain.repository;

import com.example.erp.domain.model.UnprocessedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<UnprocessedOrder, Long> {}