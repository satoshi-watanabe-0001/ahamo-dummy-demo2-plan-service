package com.ahamo.dummy.demo2.template.repository;

import com.ahamo.dummy.demo2.template.entity.OverseaCallingOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OverseaCallingOptionRepository extends JpaRepository<OverseaCallingOptionEntity, String> {
    List<OverseaCallingOptionEntity> findByIsActiveTrue();
}
