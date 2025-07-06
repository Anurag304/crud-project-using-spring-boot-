package com.example.crud_spring.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud_spring.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {}
