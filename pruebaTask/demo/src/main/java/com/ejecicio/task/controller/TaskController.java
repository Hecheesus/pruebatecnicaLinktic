package com.ejecicio.task.controller;

import com.ejecicio.task.dto.TaskDTO;
import com.ejecicio.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
// @Tag organiza los endpoints en la interfaz de Swagger bajo un nombre y descripción
@Tag(name = "Gestión de Tareas", description = "Endpoints para crear, leer, actualizar y eliminar tareas")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Crear una nueva tarea", description = "Guarda una tarea en la base de datos y retorna la tarea creada con su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.save(taskDTO);
    }

    @Operation(summary = "Listar todas las tareas", description = "Retorna una lista completa de las tareas registradas.")
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.findAll();
    }

    @Operation(summary = "Obtener tarea por ID", description = "Busca una tarea específica por su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea encontrada"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    @GetMapping("/{id}")
    public TaskDTO getTaskById(
            @Parameter(description = "ID de la tarea a buscar", required = true, example = "1")
            @PathVariable Long id) {
        return taskService.findById(id);
    }

    @Operation(summary = "Actualizar una tarea", description = "Actualiza los datos de una tarea existente basada en su ID.")
    @PutMapping("/{id}")
    public TaskDTO updateTask(
            @Parameter(description = "ID de la tarea a actualizar") @PathVariable Long id,
            @RequestBody TaskDTO taskDTO) {
        return taskService.update(id, taskDTO);
    }

    @Operation(summary = "Eliminar una tarea", description = "Elimina permanentemente una tarea de la base de datos.")
    @DeleteMapping("/{id}")
    public void deleteTask(
            @Parameter(description = "ID de la tarea a eliminar") @PathVariable Long id) {
        taskService.delete(id);
    }
}
