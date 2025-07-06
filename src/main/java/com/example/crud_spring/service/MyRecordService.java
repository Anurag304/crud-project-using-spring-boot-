package com.example.crud_spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud_spring.model.MyRecord;
import com.example.crud_spring.repository.MyRecordRepository;

@Service
public class MyRecordService {

    @Autowired
    private MyRecordRepository recordRepository;

    public MyRecord saveRecord(MyRecord record) {
        return recordRepository.save(record);
    }

    public List<MyRecord> getAllRecords() {
        return recordRepository.findAll();
    }

    public Optional<MyRecord> getRecordById(Long id) {
        return recordRepository.findById(id);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

    public List<MyRecord> getRecordsByActive(boolean active) {
        return recordRepository.findByActive(active);
    }

    public List<MyRecord> searchRecordsByName(String name) {
        return recordRepository.findByNameContainingIgnoreCase(name);
    }
}
