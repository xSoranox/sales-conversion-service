package com.example.sales.repository;

import com.example.sales.entity.SalesDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesDataJpaRepository extends JpaRepository<SalesDataEntity, Integer> {

    List<SalesDataEntity> findByTrackingIdInAndVisitDateBetween(List<String> trackingIds, LocalDateTime fromDate, LocalDateTime toDate);

    List<SalesDataEntity> findByVisitDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
