package com.example.demo.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileUtility {

    public FileUtility(){

    }
    public static void saveGameStatus (String levelName){
        try {
            // Define the path to the file in a platform-independent way
            Path savedStatusPath = Paths.get("src", "main", "resources", "gameStatus", "gameStatus.txt");

            // Print resolved absolute path
            System.out.println("Resolved Path: " + savedStatusPath.toAbsolutePath());

            // Ensure parent directories exist
            Files.createDirectories(savedStatusPath.getParent());
            System.out.println("Parent directories ensured.");

            if(!Files.exists(savedStatusPath)){
                // Attempt to create the file unconditionally
                Files.createFile(savedStatusPath); // Force file creation
                System.out.println("File created successfully at: " + savedStatusPath.toAbsolutePath());
            }

            // Write to the file
            try (FileWriter fileWriter = new FileWriter(savedStatusPath.toFile(), false)) {
                fileWriter.write(levelName);
                System.out.println("Game level saved successfully!");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to save game status", ex);
        }
    }

    public static String readGameStatus(){



        File savedStatusFile = new File(DataUtilities.GameFile);


        // Debugging: Check if the file exists and print the file path
        System.out.println("Looking for file at: " + savedStatusFile.getAbsolutePath());

        if (savedStatusFile.exists()) {
            System.out.println("File exists!");

            try (Scanner scanner = new Scanner(savedStatusFile)) {
                // Read the first line from the file
                if (scanner.hasNextLine()) {
                    String levelName = scanner.nextLine().trim(); // Clean up any spaces/newlines

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
