package com.ahamo.dummy.demo2.template.repository;

import com.ahamo.dummy.demo2.template.entity.VoiceOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoiceOptionRepository extends JpaRepository<VoiceOptionEntity, String> {
    List<VoiceOptionEntity> findByIsActiveTrue();
}
