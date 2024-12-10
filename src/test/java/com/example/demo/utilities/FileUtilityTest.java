package com.example.demo.utilities;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for FileUtility class.
 */
public class FileUtilityTest {

    private static Path tempDir;
    private static String testFilePath;

    @BeforeAll
    static void setUpClass() throws IOException {
        // Create a temporary directory for testing
        tempDir = Files.createTempDirectory("fileUtilityTest");
        testFilePath = tempDir.resolve("gameStatus.txt").toString();
        DataUtilities.GameFile = testFilePath; // Redirect game file path to the test directory
    }

    @AfterAll
    static void tearDownClass() throws IOException {
        // Clean up the temporary directory after all tests
        Files.walk(tempDir)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @BeforeEach
    void setUp() throws IOException {
        // Ensure the test environment is clean before each test
        Path filePath = Paths.get(testFilePath);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }

    @Test
    void testSaveGameStatus_createsFileAndSavesData() {
        String testLevelName = "TestLevel";

        // Call saveGameStatus
        FileUtility.saveGameStatus(testLevelName);

        // Check if file exists
        Path savedFile = Paths.get(testFilePath);
        assertTrue(Files.exists(savedFile), "Game status file should exist after saving");

        // Read back the file and verify contents
        try {
            String savedContent = Files.readString(savedFile);
            assertEquals(testLevelName, savedContent, "Saved content should match the level name");
        } catch (IOException e) {
            fail("Failed to read saved file: " + e.getMessage());
        }
    }

    @Test
    void testReadGameStatus_returnsDefaultWhenFileNotExists() {
        // Ensure file does not exist
        Path savedFile = Paths.get(testFilePath);
        if (Files.exists(savedFile)) {
            try {
                Files.delete(savedFile);
            } catch (IOException e) {
                fail("Failed to delete test file for clean state.");
            }
        }

        // Call readGameStatus
        String result = FileUtility.readGameStatus();

        // Assert it returns the default level if file is absent
        assertEquals(DataUtilities.LevelOne, result, "Should return the default level if the file doesn't exist");
    }

    @Test
    void testReadGameStatus_whenFileExistsAndHasValidData() throws IOException {
        String testLevelName = "SavedTestLevel";
        Files.createDirectories(Paths.get(tempDir.toString()));
        Files.write(Paths.get(testFilePath), testLevelName.getBytes());

        // Call readGameStatus
        String result = FileUtility.readGameStatus();

        // Verify returned level
        assertEquals(testLevelName, result, "Should return the saved level name if it exists");
    }

    @Test
    void testReadGameStatus_whenFileIsEmpty() throws IOException {
        // Create an empty file
        Files.createDirectories(Paths.get(tempDir.toString()));
        Files.write(Paths.get(testFilePath), new byte[0]);

        // Call readGameStatus
        String result = FileUtility.readGameStatus();

        // Assert it should return the default value
        assertEquals(DataUtilities.LevelOne, result, "Should return the default level if the file is empty");
    }

    @Test
    void testSaveGameStatus_createsDirectories() throws IOException {
        String subDirTestPath = tempDir.resolve("subdir").resolve("gameStatus.txt").toString();
        DataUtilities.GameFile = subDirTestPath;

        // Call saveGameStatus
        FileUtility.saveGameStatus("SubDirTest");

        // Verify that subdirectories are created
        Path subDir = tempDir.resolve("subdir");
        assertTrue(Files.exists(subDir) && Files.isDirectory(subDir), "Subdirectories should have been created");
    }
}
