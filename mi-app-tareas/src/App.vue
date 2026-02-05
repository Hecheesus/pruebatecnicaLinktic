<template>
  <div class="app-container">
    <div class="main-content">
      <header class="header">
        <h1>🚀 Gestión de Tareas</h1>
        <p class="subtitle">Conectado a API: puerto 8081</p>
      </header>

      <!-- FORMULARIO DE CREACIÓN/EDICIÓN -->
      <div class="card form-card">
        <h2 class="card-title">
          {{ isEditing ? '✏️ Editar Tarea' : '➕ Nueva Tarea' }}
        </h2>
        
        <form @submit.prevent="handleSubmit" class="task-form">
          <div class="input-group">
            <label>Título</label>
            <input 
              v-model="form.title" 
              type="text" 
              required 
              placeholder="Ej: Aprender Vue.js"
            />
          </div>

          <div class="input-group">
            <label>Descripción</label>
            <textarea 
              v-model="form.description" 
              rows="2" 
              placeholder="Detalles de la tarea..."
            ></textarea>
          </div>

          <div class="form-actions">
            <button type="submit" class="btn btn-primary">
              {{ isEditing ? 'Guardar Cambios' : 'Crear Tarea' }}
            </button>
            <button 
              v-if="isEditing" 
              type="button" 
              class="btn btn-secondary" 
              @click="cancelEdit"
            >
              Cancelar
            </button>
          </div>
        </form>
      </div>

      <!-- LISTA DE TAREAS -->
      <div class="card list-card">
        <h2 class="card-title">📋 Tus Tareas</h2>

        <div v-if="loading" class="loading">Cargando datos...</div>
        
        <div v-else-if="tasks.length === 0" class="empty-state">
          No hay tareas registradas. ¡Agrega una nueva!
        </div>

        <div v-else class="tasks-grid">
          <div 
            v-for="task in tasks" 
            :key="task.id" 
            class="task-item"
            :class="{ 'task-completed': task.completed }"
          >
            <div class="task-info">
              <h3>{{ task.title }}</h3>
              <p>{{ task.description }}</p>
              <span class="status-badge" :class="task.completed ? 'done' : 'pending'">
                {{ task.completed ? 'Completada' : 'Pendiente' }}
              </span>
            </div>

            <div class="task-actions">
              <button 
                @click="toggleCompleted(task)" 
                class="btn-icon"
                :title="task.completed ? 'Marcar pendiente' : 'Marcar completada'"
              >
                {{ task.completed ? '↩️' : '✅' }}
              </button>
              
              <button 
                @click="startEdit(task)" 
                class="btn-icon"
                title="Editar"
              >
                ✏️
              </button>
              
              <button 
                @click="deleteTask(task.id)" 
                class="btn-icon delete"
                title="Eliminar"
              >
                🗑️
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// --- CONFIGURACIÓN ---
const API_URL = 'http://localhost:8081/tasks'


// --- ESTADO ---
const tasks = ref([])
const loading = ref(false)
const isEditing = ref(false)
const editingId = ref(null)

const form = ref({
  title: '',
  description: ''
})

// --- FUNCIONES ---

// 1. Cargar tareas
const fetchTasks = async () => {
  loading.value = true
  try {
    const response = await axios.get(API_URL)
    tasks.value = response.data
  } catch (error) {
    console.error(error)
    alert('Error conectando con la API (Puerto 8081). Revisa la consola.')
  } finally {
    loading.value = false
  }
}

// 2. Guardar (Crear o Editar)
const handleSubmit = async () => {
  try {
    if (isEditing.value) {
      await axios.put(`${API_URL}/${editingId.value}`, {
        id: editingId.value,
        title: form.value.title,
        description: form.value.description,
        completed: tasks.value.find(t => t.id === editingId.value)?.completed
      })
    } else {
      await axios.post(API_URL, {
        title: form.value.title,
        description: form.value.description,
        completed: false
      })
    }
    await fetchTasks()
    resetForm()
  } catch (error) {
    alert('Error al guardar: ' + error.message)
  }
}

// 3. Eliminar
const deleteTask = async (id) => {
  if(!confirm('¿Eliminar esta tarea?')) return
  try {
    await axios.delete(`${API_URL}/${id}`)
    tasks.value = tasks.value.filter(t => t.id !== id)
  } catch (error) {
    alert('Error al eliminar')
  }
}

// 4. Marcar completada/pendiente
const toggleCompleted = async (task) => {
  try {
    const updated = { ...task, completed: !task.completed }
    await axios.put(`${API_URL}/${task.id}`, updated)
    // Actualizar localmente sin recargar todo
    const index = tasks.value.findIndex(t => t.id === task.id)
    if (index !== -1) tasks.value[index] = updated
  } catch (error) {
    alert('Error al cambiar estado')
  }
}

// --- UTILIDADES ---
const startEdit = (task) => {
  isEditing.value = true
  editingId.value = task.id
  form.value.title = task.title
  form.value.description = task.description
}

const cancelEdit = () => {
  resetForm()
}

const resetForm = () => {
  form.value.title = ''
  form.value.description = ''
  isEditing.value = false
  editingId.value = null
}

// Cargar al inicio
onMounted(() => {
  fetchTasks()
})
</script>

<style>
/* Estilos Globales Simples */
:root {
  --primary: #6366f1; /* Indigo */
  --bg: #f8fafc;
  --text: #334155;
}

body {
  font-family: sans-serif;
  background-color: var(--bg);
  color: var(--text);
  margin: 0;
  padding: 20px;
}

.app-container {
  max-width: 800px;
  margin: 0 auto;
}

.header { text-align: center; margin-bottom: 30px; }
.header h1 { margin: 0; color: var(--primary); }
.subtitle { color: #64748b; margin-top: 5px; }

/* Tarjetas */
.card {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1);
  margin-bottom: 20px;
}

.card-title { margin-top: 0; border-bottom: 1px solid #e2e8f0; padding-bottom: 10px; }

/* Formulario */
.input-group { margin-bottom: 15px; }
.input-group label { display: block; font-weight: bold; margin-bottom: 5px; }
input, textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #cbd5e1;
  border-radius: 6px;
  box-sizing: border-box;
}

.form-actions { display: flex; gap: 10px; }
.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
}
.btn-primary { background: var(--primary); color: white; flex: 1; }
.btn-secondary { background: #94a3b8; color: white; }

/* Lista */
.task-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  margin-bottom: 10px;
  background: #fff;
}

.task-completed { opacity: 0.6; background: #f1f5f9; }
.task-completed h3 { text-decoration: line-through; }

.task-info h3 { margin: 0 0 5px 0; font-size: 1.1em; }
.task-info p { margin: 0; color: #64748b; font-size: 0.9em; }

.status-badge {
  display: inline-block;
  font-size: 0.75em;
  padding: 2px 8px;
  border-radius: 10px;
  margin-top: 5px;
}
.status-badge.done { background: #dcfce7; color: #166534; }
.status-badge.pending { background: #fee2e2; color: #991b1b; }

.task-actions { display: flex; gap: 5px; }
.btn-icon {
  background: #e2e8f0;
  border: none;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}
.btn-icon:hover { background: #cbd5e1; }
.delete:hover { background: #fee2e2; }
</style>
