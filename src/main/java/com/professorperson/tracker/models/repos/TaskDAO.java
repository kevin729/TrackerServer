package com.professorperson.tracker.models.repos;

import com.professorperson.tracker.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskDAO extends JpaRepository<Task, Integer> {

}
