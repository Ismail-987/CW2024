package com.example.demo.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Utility class for handling file operations related to game status.
 */
public class FileUtility {

    /**
     * Default constructor.
     */
    public FileUtility() {
    }

    /**
     * Saves the current game level name to a file.
     *
     * @param levelName The name of the level to be saved.
     */
    public static void saveGameStatus(String levelName) {
        try {
            Path savedStatusPath = Paths.get("src", "main", "resources", "gameStatus", "gameStatus.txt");
            System.out.println("Resolved Path: " + savedStatusPath.toAbsolutePath());
            Files.createDirectories(savedStatusPath.getParent());
            System.out.println("Parent directories ensured.");

            if (!Files.exists(savedStatusPath)) {
                Files.createFile(savedStatusPath);
                System.out.println("File created successfully at: " + savedStatusPath.toAbsolutePath());
            }

            try (FileWriter fileWriter = new FileWriter(savedStatusPath.toFile(), false)) {
                fileWriter.write(levelName);
                System.out.println("Game level saved successfully!");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to save game status", ex);
        }
    }

    /**
     * Reads the saved game level name from a file.
     *
     * @return The name of the saved level, or a default level if not found.
     */
    public static String readGameStatus() {
        File savedStatusFile = new File(DataUtilities.GameFile);
        System.out.println("Looking for file at: " + savedStatusFile.getAbsolutePath());

        if (savedStatusFile.exists()) {
            System.out.println("File exists!");

            try (Scanner scanner = new Scanner(savedStatusFile)) {
                if (scanner.hasNextLine()) {
                    String levelName = scanner.nextLine().trim();
                    if (!levelName.isEmpty()) {
                        return levelName;
                    } else {
                        System.out.println("No level found. Loading default level.");
                        return DataUtilities.LevelOne;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Failed to read game status", ex);
            }
        }

        System.out.println("File does not exist. Loading default level.");
        return DataUtilities.LevelOne;
    }
}