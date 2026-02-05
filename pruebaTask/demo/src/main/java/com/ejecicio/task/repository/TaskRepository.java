package com.ejecicio.task.repository;

import com.ejecicio.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findByTitle(String title);
}
