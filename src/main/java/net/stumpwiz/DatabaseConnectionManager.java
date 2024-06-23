/*
 * Copyright (c) 2024 George Wright.  Created 2024-06-22.
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnectionManager
{
    private static final Map<String, DatabaseConnectionManager> instances = new HashMap<>();
    static Connection connection;
    private final String url;

    private DatabaseConnectionManager(String url)
    {
        this.url = url;
    }

    /**
     * Retrieves or creates an instance of DatabaseConnectionManager for the specified environment.
     * If an instance for the environment already exists, it is retrieved. Otherwise, a new instance is created.
     *
     * @param environment The environment for which to retrieve or create an instance.
     * @return The DatabaseConnectionManager instance associated with the specified environment.
     */
    public static DatabaseConnectionManager getInstance(String environment)
    {
        // Synchronized block to prevent multiple threads from simultaneously creating instances
        synchronized (DatabaseConnectionManager.class) {
            // Check if an instance for this environment already exists
            if (!instances.containsKey(environment)) {
                if ("PROD".equals(environment)) {
                    instances.put(environment, new DatabaseConnectionManager(
                            Configuration.getInstance().getWorkingUrl()));
                } else if ("TEST".equals(environment)) {
                    instances.put(environment, new DatabaseConnectionManager(
                            Configuration.getInstance().getTestUrl()));
                }
            }
        }
        // Retrieve the instance for this environment
        return instances.get(environment);
    }

    /**
     * Retrieves a connection to the database. If a connection does not exist or is closed,
     * it will create a new connection.
     *
     * @return The connection object
     * @throws SQLException If an error occurs while creating or retrieving the connection
     */
    public Connection getConnection() throws SQLException
    {
        if (connection == null || connection.isClosed()) connect();
        return connection;
    }

    /**
     * Establishes a connection to the database.
     *
     * @throws SQLException If an error occurs while creating the connection
     */
    private void connect() throws SQLException
    {
        try {
            connection = DriverManager.getConnection(this.url);
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    /**
     * Closes the database connection if it is open.
     *
     * @throws SQLException If an error occurs while closing the connection
     */
    public void closeConnection() throws SQLException
    {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
