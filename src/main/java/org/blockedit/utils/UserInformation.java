/*
BlockEdit, a general Minecraft program that is in heavy development
    Copyright (C) 2015  Jeff Chen

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockedit.utils;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Miscellaneous methods that gets the information of the user.
 *
 * @author Jeff Chen
 */
public class UserInformation {

    /**
     * Get the window width of the user's monitor.
     *
     * @return The window width in pixels
     */
    public static double getWindowWidth() {
        return (double) GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
    }

    /**
     * Get the window height of the user's monitor.
     *
     * @return The window height in pixels
     */
    public static double getWindowHeight() {
        return (double) GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
    }

    /**
     * Checks if the user has internet or not. If the user has internet, it will return <code>true</code>.
     *
     * @return If the user has internet or not
     */
    public static boolean hasInternet() {
        try {
            URL url = new URL("http://www.google.ca");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            return connection.getResponseCode() == 200;
        } catch (IOException e) {
            return false;
        }
    }
}
