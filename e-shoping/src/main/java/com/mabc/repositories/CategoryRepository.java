package com.mabc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mabc.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}