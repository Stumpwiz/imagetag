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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static net.stumpwiz.Functions.errorAlert;

public class Main
{
    static Properties configProperties = new Properties();
    static String TEST_URL;
    static String WORKING_URL;

    public static void main(String[] args)
    {
        configProperties = new Properties();
        try (InputStream input = new FileInputStream("../config.properties")) {
            configProperties.load(input);
        } catch (IOException ex) {
            errorAlert(ex.getMessage());
        }
        System.out.println(configProperties);
        TEST_URL = configProperties.getProperty("TEST_URL");
        WORKING_URL = configProperties.getProperty("WORKING_URL");
        System.out.println(TEST_URL);
        System.out.println(WORKING_URL);
    }
}