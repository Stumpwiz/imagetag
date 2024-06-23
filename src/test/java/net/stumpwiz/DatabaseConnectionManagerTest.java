package net.stumpwiz;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the DatabaseConnectionManager class.
 * Messages prefixed "SLF4J:" may be ignored.  They are issued by other libraries and can't be
 * easily suppressed.
 * <p>
 * Messages prefixed "WARNING" are providing information about low-level operations that Mockito
 * and Byte Buddy are performing. They don't indicate a problem with your test or your code, and
 * you can typically ignore them.
 */
class DatabaseConnectionManagerTest
{
    @Test
    public void testGetInstance()
    {
        DatabaseConnectionManager manager1 = DatabaseConnectionManager.getInstance("PROD");
        DatabaseConnectionManager manager2 = DatabaseConnectionManager.getInstance("PROD");
        assertSame(manager1, manager2);
    }

    @Test
    public void testGetInstanceWithDifferentEnvironments()
    {
        DatabaseConnectionManager manager1 = DatabaseConnectionManager.getInstance("PROD");
        DatabaseConnectionManager manager2 = DatabaseConnectionManager.getInstance("TEST");
        assertNotSame(manager1, manager2);
    }

    @Test
    void getConnection()
    {
        // given the DatabaseConnectionManager instances for "PROD" and "TEST" environments.
        DatabaseConnectionManager managerProd = DatabaseConnectionManager.getInstance("PROD");
        DatabaseConnectionManager managerTest = DatabaseConnectionManager.getInstance("TEST");

        try {
            // when getting connections.
            Connection connectionProd = managerProd.getConnection();
            Connection connectionTest = managerTest.getConnection();

            // then they should not be null and should be open.
            assertNotNull(connectionProd);
            assertFalse(connectionProd.isClosed());

            assertNotNull(connectionTest);
            assertFalse(connectionTest.isClosed());

            // Clean up after the test by closing the connections.
            connectionProd.close();
            connectionTest.close();
        } catch (SQLException e) {
            fail("Failed to open or close connection due to an exception: " + e.getMessage());
        }
    }

    @Test
    void closeConnection()
    {
        DatabaseConnectionManager managerProd = DatabaseConnectionManager.getInstance("PROD");
        DatabaseConnectionManager managerTest = DatabaseConnectionManager.getInstance("TEST");

        try {
            // when getting connections.
            Connection connectionProd = managerProd.getConnection();
            Connection connectionTest = managerTest.getConnection();

            // then they should not be null and should be open.
            assertNotNull(connectionProd);
            assertFalse(connectionProd.isClosed());

            assertNotNull(connectionTest);
            assertFalse(connectionTest.isClosed());

            // when closing connections
            managerProd.closeConnection();
            managerTest.closeConnection();

            // then they should be closed
            assertTrue(connectionProd.isClosed());
            assertTrue(connectionTest.isClosed());

        } catch (SQLException e) {
            fail("Failed to open, close, or check status of the connection due to an exception: " + e.getMessage());
        }
    }
}