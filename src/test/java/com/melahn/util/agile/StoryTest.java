package com.melahn.util.agile;

import org.junit.Test;
import org.junit.BeforeClass;

import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.fail;

public class StoryTest {
    static String testDirName = "target/test";
    static String testDirGeneratedFileName = "user-story-generated.txt";
    static File testDir = new File(testDirName);
    File generated = new File(testDirName + "/" + testDirGeneratedFileName);

    @BeforeClass
    /**
     * Creates a new test directory for the generated test files.
     */
    public static void setUp() {
        try {
            Files.deleteIfExists(testDir.toPath());
            try {
                Files.createDirectory(testDir.toPath());
            } catch (Exception e) {
                System.out.println("Error creating test directory " + testDir.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Test setup failed: " + e.getMessage());
        }
    }
    /**
     * Tests the creation of a set of user stories based on Normalized data.  Does a simple comparison
     * of the generated data to the expected data using the examples.
     */
    @Test
    public void testNormalized() {
        File expected = new File("./resource/example/user-story-generated.txt");
        String[] args = {
                "-d",
                "./resource/example/user-story-normalized-data.xlsx",
                "-o",
                testDirName + "/" + testDirGeneratedFileName,
                "-s",
                "./resource/example/user-story-normalized-data.csv"
        };
        StoryGenerator.main(args);

        if (!generated.exists()) {
            fail(new String(generated.getAbsolutePath() + " was not generated"));
        }
        if (generated.length() != expected.length()) {
            fail("Test Case Failure: The length of the generated file (" + generated.length() + ") does not match the length expected (" + expected.length() + ").");
        }
    }

    @Test
    /**
     * Tests the creation of a set of user stories based on Denormalized data.  Does a simple comparison
     * of the generated data to the expected data using the examples.
     */
    public void testDenormalized() {
        File expected = new File("./resource/example/user-story-generated.txt");
        String[] args = {
                "-d",
                "./resource/example/user-story-normalized-data.xlsx",
                "-o",
                testDirName + "/" + testDirGeneratedFileName
        };
        StoryGenerator.main(args);

        if (!generated.exists()) {
            fail(new String(generated.getAbsolutePath() + " was not generated"));
        }
        if (generated.length() != expected.length()) {
            fail("Test Case Failure: The length of the generated file (" + generated.length() + ") does not match the length expected (" + expected.length() + ").");
        }
    }
}

