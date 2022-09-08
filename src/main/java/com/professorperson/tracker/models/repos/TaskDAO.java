package com.professorperson.tracker.models.repos;

import com.professorperson.tracker.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDAO extends JpaRepository<Task, Integer> {
}
