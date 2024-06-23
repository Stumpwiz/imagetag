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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Functions
{
    /**
     * Initializes the test database by executing the SQL script "dbinit.sql".
     * The script is read from the file located at the path "src/main/resources/scripts/dbinit.sql".
     * The database connection is established using the TEST_URL constant.
     *
     * @throws IOException      if an I/O error occurs when reading the SQL script file
     * @throws RuntimeException if a database access error occurs
     */
    static void initTestDb() throws IOException
    {
        Path path = Paths.get("src/main/resources/scripts/dbinit.sql");
        String script;
        try {
            script = Files.readString(path);
            DatabaseConnectionManager managerTest =
                    DatabaseConnectionManager.getInstance("TEST");
            Connection connectionTest = managerTest.getConnection();
            Statement statement = connectionTest.createStatement();
            for (String singleQuery : script.split(";")) {
                if (!singleQuery.trim().isEmpty()) {
                    statement.execute(singleQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays an error alert with the given message.
     * <p>
     * Will ultimately be replaced with code to open a JavaFX dialog box.
     * </p>
     *
     * @param message The error message to be displayed
     */
    static void errorAlert(String message)
    {
        System.out.println(message);
    }
}
