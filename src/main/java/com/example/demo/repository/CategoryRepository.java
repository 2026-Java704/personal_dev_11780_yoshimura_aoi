package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Category;

public interface AccountRepository extends JpaRepository<Category, Integer> {
}
