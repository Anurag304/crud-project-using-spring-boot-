package com.example.crud_spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.crud_spring.model.MyRecord;
import com.example.crud_spring.service.MyRecordService;

@RestController
@RequestMapping("/api/records")
@CrossOrigin(origins = "http://localhost:3000") // adjust if needed
public class MyRecordController {

    @Autowired
    private MyRecordService recordService;

    // Create a new record
    @PostMapping
    public ResponseEntity<MyRecord> createRecord(@RequestBody MyRecord record) {
        MyRecord savedRecord = recordService.saveRecord(record);
        return ResponseEntity.ok(savedRecord);
    }

    // Get all records
    @GetMapping
    public List<MyRecord> getAllRecords() {
        return recordService.getAllRecords();
    }

    // Filter by active/inactive
    @GetMapping("/filter")
    public List<MyRecord> getRecordsByActive(@RequestParam boolean active) {
        return recordService.getRecordsByActive(active);
    }

    // Search by name
    @GetMapping("/search")
    public List<MyRecord> searchRecordsByName(@RequestParam String name) {
        return recordService.searchRecordsByName(name);
    }

    // Get record by ID (now only matches numeric IDs to prevent collisions)
    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<MyRecord> getRecordById(@PathVariable Long id) {
        Optional<MyRecord> record = recordService.getRecordById(id);
        return record.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a record
    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<MyRecord> updateRecord(@PathVariable Long id, @RequestBody MyRecord updatedRecord) {
        Optional<MyRecord> existingRecordOpt = recordService.getRecordById(id);

        if (existingRecordOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MyRecord existingRecord = existingRecordOpt.get();
        existingRecord.setName(updatedRecord.getName());
        existingRecord.setDescription(updatedRecord.getDescription());
        existingRecord.setCategory(updatedRecord.getCategory());
        existingRecord.setActive(updatedRecord.isActive());

        MyRecord savedRecord = recordService.saveRecord(existingRecord);
        return ResponseEntity.ok(savedRecord);
    }

    // Delete a record
    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        if (recordService.getRecordById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        recordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }

    // Bulk delete
    @PostMapping("/bulk-delete")
    public ResponseEntity<Void> bulkDelete(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            recordService.deleteRecord(id);
        }
        return ResponseEntity.noContent().build();
    }
}
