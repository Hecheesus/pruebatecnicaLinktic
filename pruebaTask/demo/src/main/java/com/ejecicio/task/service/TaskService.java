package com.ejecicio.task.service;

import com.ejecicio.task.dto.TaskDTO;
import com.ejecicio.task.exception.TaskNotFoundExcepcion;
import com.ejecicio.task.model.Task;
import com.ejecicio.task.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    public List<TaskDTO> findAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(this::mapToDTO).toList();
    }

    public TaskDTO findById(Long id) {
        // Usa la excepción personalizada con un mensaje descriptivo


        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundExcepcion("No se encontró la tarea con ID: " + id));

        return mapToDTO(task);
    }

    public TaskDTO save(TaskDTO taskDTO) {
        Task task = mapToEntity(taskDTO);
        // Es buena práctica poner el ID en null al crear para forzar uno nuevo
        task.setId(null);
        Task savedTask = taskRepository.save(task);
        return mapToDTO(savedTask);
    }

    public TaskDTO update(Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundExcepcion("No se puede actualizar. Tarea no encontrada con ID: " + id));

        // Actualizamos solo los campos necesarios
        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setCompleted(taskDTO.isCompleted());

        Task updatedTask = taskRepository.save(existingTask);
        return mapToDTO(updatedTask);
    }

    public TaskDTO findByTitle(String title) {
        Task task = taskRepository.findByTitle(title);
        // Validamos si task es null, ya que findByTitle podría no devolver un Optional
        if (task == null) {
            throw new TaskNotFoundExcepcion("No se encontró ninguna tarea con el título: " + title);
        }
        return mapToDTO(task);
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundExcepcion("No se puede eliminar. Tarea no encontrada con ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    public TaskDTO updateStatus(Long id, boolean completed) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundExcepcion("No se encontró la tarea con ID: " + id));

        existingTask.setCompleted(completed);
        Task updatedTask = taskRepository.save(existingTask);
        return mapToDTO(updatedTask);
    }

    private TaskDTO mapToDTO(Task task) {
        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
    }

    private Task mapToEntity(TaskDTO taskDTO) {
        return new Task(taskDTO.getId(), taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.isCompleted());
    }
}
