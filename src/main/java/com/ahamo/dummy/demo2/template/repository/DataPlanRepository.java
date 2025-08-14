package com.ahamo.dummy.demo2.template.repository;

import com.ahamo.dummy.demo2.template.entity.DataPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataPlanRepository extends JpaRepository<DataPlan, String> {
    List<DataPlan> findByIsActiveTrue();
}
