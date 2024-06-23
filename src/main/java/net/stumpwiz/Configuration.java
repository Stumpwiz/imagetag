/*
 * Copyright (c) 2024 George Wright.  Created 2024-06-23.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.stumpwiz;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The Configuration class represents a configuration file that loads properties from a file.
 * It provides methods to retrieve working and test URLs.
 */
public class Configuration
{
    private static Configuration instance;
    private String working_url;
    private String test_url;

    /**
     * Represents a configuration class that loads properties from a file.
     * It provides methods to retrieve working and test URLs.
     */
    private Configuration()
    {
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("config.properties"));
            working_url = config.getProperty("WORKING_URL");
            test_url = config.getProperty("TEST_URL");
        } catch (IOException ex) {
            Functions.errorAlert(ex.getMessage());
        }
    }

    /**
     * Retrieves the instance of the Configuration class.
     *
     * @return The instance of the Configuration class
     */
    public static synchronized Configuration getInstance()
    {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    /**
     * Retrieves the working URL from the Configuration class.
     *
     * @return The working URL
     */
    public String getWorkingUrl()
    {
        return working_url;
    }

    /**
     * Retrieves the test URL from the configuration.
     *
     * @return The test URL
     */
    public String getTestUrl()
    {
        return test_url;
    }
}
