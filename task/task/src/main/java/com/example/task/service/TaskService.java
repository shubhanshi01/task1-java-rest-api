package com.example.task.service;

import com.example.task.model.Task;
import com.example.task.model.TaskExecution;
import com.example.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommandValidationService commandValidationService;

    @Autowired
    private CommandExecutionService commandExecutionService;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public List<Task> findTasksByName(String name) {
        return taskRepository.findByNameContainingIgnoreCase(name);
    }

    public Task createTask(Task task) {
        // Validate command
        if (!commandValidationService.isCommandSafe(task.getCommand())) {
            throw new IllegalArgumentException("Unsafe command detected: " + task.getCommand());
        }

        // Sanitize command
        task.setCommand(commandValidationService.sanitizeCommand(task.getCommand()));

        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {
        if (!taskRepository.existsById(task.getId())) {
            throw new IllegalArgumentException("Task not found with id: " + task.getId());
        }

        // Validate command
        if (!commandValidationService.isCommandSafe(task.getCommand())) {
            throw new IllegalArgumentException("Unsafe command detected: " + task.getCommand());
        }

        // Sanitize command
        task.setCommand(commandValidationService.sanitizeCommand(task.getCommand()));

        return taskRepository.save(task);
    }

    public boolean deleteTask(String id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TaskExecution executeTask(String taskId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isEmpty()) {
            throw new IllegalArgumentException("Task not found with id: " + taskId);
        }

        Task task = taskOpt.get();

        // Execute the command
        TaskExecution execution = commandExecutionService.executeCommand(task.getCommand());

        // Add execution to task
        task.addTaskExecution(execution);
        taskRepository.save(task);

        return execution;
    }
}