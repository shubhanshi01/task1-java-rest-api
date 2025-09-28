package com.example.task.service;

import com.example.task.model.TaskExecution;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class CommandExecutionService {

    public TaskExecution executeCommand(String command) {
        LocalDateTime startTime = LocalDateTime.now();
        StringBuilder output = new StringBuilder();
        LocalDateTime endTime;

        try {
            // Create process builder for cross-platform compatibility
            ProcessBuilder processBuilder;
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                processBuilder = new ProcessBuilder("cmd", "/c", command);
            } else {
                processBuilder = new ProcessBuilder("bash", "-c", command);
            }

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Read output
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            // Wait for command completion with timeout
            boolean finished = process.waitFor(30, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                output.append("Command execution timed out after 30 seconds");
            }

            endTime = LocalDateTime.now();

        } catch (IOException | InterruptedException e) {
            endTime = LocalDateTime.now();
            output.append("Error executing command: ").append(e.getMessage());
        }

        return new TaskExecution(startTime, endTime, output.toString());
    }
}