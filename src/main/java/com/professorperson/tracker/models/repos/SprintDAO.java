package com.professorperson.tracker.models.repos;

import com.professorperson.tracker.models.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintDAO extends JpaRepository<Sprint, Integer> {
}
