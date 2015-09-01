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

import java.io.IOException;
import java.util.Properties;

public class VersionReference {
    public static String getVersion() {
        try {
            final Properties properties = new Properties();
            properties.load(VersionReference.class.getClassLoader().getResourceAsStream("project.properties"));
            return properties.getProperty("version");
        } catch (IOException e) {
            return "null";
        }
    }
    public static final boolean DEV = true;
    public static final boolean PRE_RELEASE = false;
    public static final boolean RELEASE = false;
}
