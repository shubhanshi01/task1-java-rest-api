package com.example.task.controller;

import com.example.task.model.Task;
import com.example.task.model.TaskExecution;
import com.example.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // GET /api/tasks - Get all tasks or specific task by ID
    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String id) {
        if (id != null && !id.isEmpty()) {
            Optional<Task> task = taskService.getTaskById(id);
            if (task.isPresent()) {
                return ResponseEntity.ok(task.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            List<Task> tasks = taskService.getAllTasks();
            return ResponseEntity.ok(tasks);
        }
    }

    // GET /api/tasks/search?name={name} - Find tasks by name
    @GetMapping("/search")
    public ResponseEntity<List<Task>> findTasksByName(@RequestParam String name) {
        List<Task> tasks = taskService.findTasksByName(name);
        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tasks);
    }

    // PUT /api/tasks - Create or update a task
    @PutMapping
    public ResponseEntity<?> createOrUpdateTask(@Valid @RequestBody Task task, BindingResult result) {
        System.out.println("=== Received PUT request ===");
        System.out.println("Task: " + task);

        if (result.hasErrors()) {
            System.out.println("Validation errors found:");
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                System.out.println("Field: " + error.getField() + ", Error: " + error.getDefaultMessage());
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            Task savedTask;
            if (task.getId() != null && taskService.getTaskById(task.getId()).isPresent()) {
                savedTask = taskService.updateTask(task);
            } else {
                savedTask = taskService.createTask(task);
            }
            return ResponseEntity.ok(savedTask);
        } catch (IllegalArgumentException e) {
            System.out.println("Service error: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // DELETE /api/tasks/{id} - Delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        boolean deleted = taskService.deleteTask(id);
        if (deleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Task deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/tasks/{id}/execute - Execute a task
    @PutMapping("/{id}/execute")
    public ResponseEntity<?> executeTask(@PathVariable String id) {
        try {
            TaskExecution execution = taskService.executeTask(id);
            return ResponseEntity.ok(execution);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error executing task: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}