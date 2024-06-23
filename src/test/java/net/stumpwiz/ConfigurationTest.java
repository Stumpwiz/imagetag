package net.stumpwiz;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ConfigurationTest class is a unit test class for the Configuration class. It tests the functionality
 * of methods in the Configuration class.
 * Messages prefixed "SLF4J:" may be ignored.  They are issued by other libraries and can't be
 * easily suppressed.
 * <p>
 * Messages prefixed "WARNING" are providing information about low-level operations that Mockito
 * and Byte Buddy are performing. They don't indicate a problem with your test or your code, and
 * you can typically ignore them.
 */
class ConfigurationTest
{
    private Configuration configuration;

    @BeforeEach
    void setUp()
    {
        configuration = Configuration.getInstance();
    }

    @AfterEach
    void tearDown()
    {
        configuration = null;
    }

    @Test
    void getInstance()
    {
        Configuration anotherConfiguration = Configuration.getInstance();
        assertSame(configuration, anotherConfiguration);
    }

    @Test
    void getWorkingUrl()
    {
        String workingUrl = configuration.getWorkingUrl();
        assertNotNull(workingUrl);
        assertFalse(workingUrl.isEmpty());
        assertEquals(workingUrl, "jdbc:sqlite:taggedImages.sqlite");
    }

    @Test
    void getTestUrl()
    {
        String testUrl = configuration.getTestUrl();
        assertNotNull(testUrl);
        assertFalse(testUrl.isEmpty());
        assertEquals(testUrl, "jdbc:sqlite::memory:");
    }
}