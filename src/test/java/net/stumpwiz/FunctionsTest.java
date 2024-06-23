package net.stumpwiz;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The FunctionsTest class is used to test the methods in the Functions class.
 * <p>
 * Messages prefixed "SLF4J:" may be ignored.  They are issued by other libraries and can't be
 * easily suppressed.
 * <p>
 * Messages prefixed "WARNING" are providing information about low-level operations that Mockito
 * and Byte Buddy are performing. They don't indicate a problem with your test or your code, and
 * you can typically ignore them.
 */
class FunctionsTest
{
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams()
    {
        System.setOut(new PrintStream(outContent)); // Capture System.out
    }

    @AfterEach
    public void restoreStreams()
    {
        System.setOut(originalOut); // Restore original System.out
    }

    @BeforeEach
    void setUp()
    {
    }

    @AfterEach
    void tearDown()
    {
    }

    @Test
    void initTestDb()
    {
        try {
            Functions.initTestDb();

            DatabaseConnectionManager managerTest =
                    DatabaseConnectionManager.getInstance("TEST");
            Connection connectionTest = managerTest.getConnection();
            Statement statement = connectionTest.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Image");
            assertTrue(rs.next(), "Expected at least one row in the result set");
            String firstImagePath = rs.getString("imagePath");
            assertEquals(firstImagePath,
                    "C:\\Users\\Geo\\OneDrive\\IdeaProjects\\tagimage\\src\\main\\resources\\img",
                    "Unexpected imagePath in the first row");
            assertTrue(rs.next(), "Expected at least two rows in the result set");
            assertFalse(rs.next(), "Expected only two rows in the result set");
        } catch (IOException | SQLException e) {
            fail("Failed to initialize and verify the test database due to an exception: " + e.getMessage());
        }
    }

    @Test
    void errorAlert()
    {
        String testErrorMessage = "Test error message";
        Functions.errorAlert(testErrorMessage);
        assertEquals(testErrorMessage + System.lineSeparator(), outContent.toString(),
                "Expected error message was not found in the output stream");
    }
}