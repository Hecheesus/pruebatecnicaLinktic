package com.ejecicio.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo de transferencia de datos para Tareas")
public class TaskDTO {

    @Schema(
            description = "Identificador único de la tarea",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Título de la tarea",
            example = "Comprar leche",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @Schema(
            description = "Descripción detallada",
            example = "Comprar leche entera y deslactosada en el supermercado",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;

    @Schema(
            description = "Estado de finalización de la tarea",
            example = "false",
            defaultValue = "false"
    )
    private boolean completed;

    // Constructor vacío
    public TaskDTO() {
    }

    // Constructor completo
    public TaskDTO(Long id, String title, String description, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
