package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneratedResumeRepository extends JpaRepository<GeneratedResume, Long> {

    /**
     * Finds all resumes and orders them by their creation timestamp in descending order (newest first).
     * Spring Data JPA automatically creates the implementation for this method based on its name.
     * "findAll" -> find all records.
     * "ByOrderBy" -> specifies the ordering.
     * "CreatedAt" -> the field to order by.
     * "Desc" -> descending order.
     */
    List<GeneratedResume> findAllByOrderByCreatedAtDesc();
}

