package com.example.task.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class CommandValidationService {

    private static final List<String> DANGEROUS_COMMANDS = Arrays.asList(
            "rm", "del", "format", "fdisk", "mkfs", "dd", "shutdown", "reboot",
            "kill", "killall", "passwd", "sudo", "su", "chmod 777", "chown"
    );

    public boolean isCommandSafe(String command) {
        if (command == null || command.trim().isEmpty()) {
            return false;
        }

        String lowerCommand = command.toLowerCase().trim();

        // Check for dangerous commands
        for (String dangerous : DANGEROUS_COMMANDS) {
            if (lowerCommand.contains(dangerous.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    public String sanitizeCommand(String command) {
        if (command == null) return "";

        // Remove multiple spaces and trim
        return command.trim().replaceAll("\\s+", " ");
    }
}