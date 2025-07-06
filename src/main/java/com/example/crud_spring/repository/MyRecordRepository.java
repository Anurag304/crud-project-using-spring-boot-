package com.example.crud_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud_spring.model.MyRecord;

public interface MyRecordRepository extends JpaRepository<MyRecord, Long> {
    List<MyRecord> findByActive(boolean active);
    List<MyRecord> findByNameContainingIgnoreCase(String name);
}
